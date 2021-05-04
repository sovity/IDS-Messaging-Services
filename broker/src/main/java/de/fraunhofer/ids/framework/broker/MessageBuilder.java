package de.fraunhofer.ids.framework.broker;

import java.net.URI;

import de.fraunhofer.iais.eis.*;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import de.fraunhofer.ids.framework.messaging.util.IdsMessageUtils;

/**
 * The MessageUtils class contains utility methods for building Infomodel Messages (used by the {@link BrokerService} class)
 */
public class MessageBuilder {

    /**
     * Utility classes (only static methods and fields) do not have a public constructor.
     * Instantiating them does not make sense, prevent instantiating.
     */
    protected MessageBuilder() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a ResourceUnavailableMessage used for unregistering the given resource at a broker
     *
     * @param securityToken the DAT Token used for this request
     * @param connector     the connector from which the message is sent
     * @param resource      the resource that is marked as unavailable at the broker
     *
     * @return the {@link ResourceUnavailableMessage}
     *
     * @throws ConstraintViolationException when the message cannot be built properly
     */
    public static ResourceUnavailableMessage buildResourceUnavailableMessage( final DynamicAttributeToken securityToken,
                                                          final Connector connector,
                                                          final Resource resource ) throws
            ConstraintViolationException {
        return new ResourceUnavailableMessageBuilder()
                ._affectedResource_(resource.getId())
                ._securityToken_(securityToken)
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._senderAgent_(connector.getId())
                ._issuerConnector_(connector.getId())
                ._modelVersion_(connector.getOutboundModelVersion())
                .build();
    }

    /**
     * Create a ResourceUpdateMessage used for registering and updating the given resource at a broker
     *
     * @param securityToken the DAT Token used for this request
     * @param connector     the connector from which the message is sent
     * @param resource      the resource that is updated at the broker
     *
     * @return the {@link ResourceUpdateMessage} as JSONLD
     *
     * @throws ConstraintViolationException when the message cannot be built properly
     */
    public static ResourceUpdateMessage buildResourceUpdateMessage( final DynamicAttributeToken securityToken,
                                                     final Connector connector,
                                                     final Resource resource ) throws ConstraintViolationException {
        return new ResourceUpdateMessageBuilder()
                ._affectedResource_(resource.getId())
                ._securityToken_(securityToken)
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._senderAgent_(connector.getId())
                ._issuerConnector_(connector.getId())
                ._modelVersion_(connector.getOutboundModelVersion())
                .build();
    }

    /**
     * Create a ConnectorUnavailableMessage used for unregistering the connector at a broker
     *
     * @param securityToken the DAT Token used for this request
     * @param connector     the connector from which the message is sent
     *
     * @return the {@link ConnectorUnavailableMessage}
     *
     * @throws ConstraintViolationException when the message cannot be built properly
     */
    public static ConnectorUnavailableMessage buildUnavailableMessage( final DynamicAttributeToken securityToken,
                                                  final Connector connector ) throws ConstraintViolationException {
        return new ConnectorUnavailableMessageBuilder()
                ._securityToken_(securityToken)
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._modelVersion_(connector.getOutboundModelVersion())
                ._issuerConnector_(connector.getId())
                ._senderAgent_(connector.getId())
                ._affectedConnector_(connector.getId())
                .build();
    }

    /**
     * Create a ConnectorUpdateMessage used for registering the connector at a broker
     *
     * @param securityToken the DAT Token used for this request
     * @param connector     the connector from which the message is sent
     *
     * @return the {@link ConnectorUpdateMessage}
     *
     * @throws ConstraintViolationException when the message cannot be serialized properly
     */
    public static ConnectorUpdateMessage buildUpdateMessage( final DynamicAttributeToken securityToken,
                                             final Connector connector ) throws ConstraintViolationException {
        return new ConnectorUpdateMessageBuilder()
                ._securityToken_(securityToken)
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._modelVersion_(connector.getOutboundModelVersion())
                ._issuerConnector_(connector.getId())
                ._senderAgent_(connector.getId())
                ._affectedConnector_(connector.getId())
                .build();
    }

    /**
     * Create a QueryMessage used for querying the broker
     *
     * @param securityToken the DAT Token used for this request
     * @param connector     the connector from which the message is sent
     * @param queryLanguage the Language of the Query (e.g. SPARQL, SQL, XQUERY)
     * @param queryScope    the Scope of the Query (ALL connectors, ACTIVE connectors, INACTIVE connectors) {@link QueryScope}
     * @param queryTarget   the type of IDS Components that are queried {@link QueryTarget}
     *
     * @return the {@link QueryMessage}
     *
     * @throws ConstraintViolationException when the message cannot be built properly
     */
    public static QueryMessage buildQueryMessage( final DynamicAttributeToken securityToken,
                                            final Connector connector,
                                            final QueryLanguage queryLanguage,
                                            final QueryScope queryScope,
                                            final QueryTarget queryTarget ) throws ConstraintViolationException {
        return new QueryMessageBuilder()
                ._securityToken_(securityToken)
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._modelVersion_(connector.getOutboundModelVersion())
                ._issuerConnector_(connector.getId())
                ._senderAgent_(connector.getId())
                ._queryLanguage_(queryLanguage)
                ._queryScope_(queryScope)
                ._recipientScope_(queryTarget)
                .build();
    }

}
