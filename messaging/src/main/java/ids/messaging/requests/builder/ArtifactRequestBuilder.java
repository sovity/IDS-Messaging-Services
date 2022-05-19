/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ids.messaging.requests.builder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import ids.messaging.common.DeserializeException;
import ids.messaging.common.SerializeException;
import ids.messaging.core.daps.ClaimsException;
import ids.messaging.core.daps.DapsTokenManagerException;
import ids.messaging.protocol.MessageService;
import ids.messaging.protocol.http.SendMessageException;
import ids.messaging.protocol.http.ShaclValidatorException;
import ids.messaging.protocol.multipart.UnknownResponseException;
import ids.messaging.protocol.multipart.parser.MultipartParseException;
import ids.messaging.requests.MessageContainer;
import ids.messaging.requests.NotificationTemplateProvider;
import ids.messaging.requests.RequestTemplateProvider;
import ids.messaging.requests.enums.Crud;
import ids.messaging.requests.enums.ProtocolType;
import ids.messaging.requests.exceptions.RejectionException;
import ids.messaging.requests.exceptions.UnexpectedPayloadException;

/**
 * RequestBuilder for messages with subject 'artifact'.
 *
 * @param <T> Type of expected Payload.
 */
public class ArtifactRequestBuilder<T> extends IdsRequestBuilder<T> implements
        ExecutableBuilder<T>,
        SupportsMultipart<T, ArtifactRequestBuilder<T>> {

    /**
     * URI of the requested artifact.
     */
    private URI requestedArtifact;

    ArtifactRequestBuilder(
            final Class<T> expected,
            final MessageService messageService,
            final RequestTemplateProvider requestTemplateProvider,
            final NotificationTemplateProvider notificationTemplateProvider) {
        super(expected, messageService, requestTemplateProvider, notificationTemplateProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArtifactRequestBuilder<T> withPayload(final Object payload) {
        this.optPayload = Optional.ofNullable(payload);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArtifactRequestBuilder<T> throwOnRejection() {
        this.throwOnRejection = true;
        return this;
    }

    /**
     * Set the operation to RECEIVE: describes an
     * {@link de.fraunhofer.iais.eis.ArtifactRequestMessage}.
     *
     * @param requestedArtifact Requested artifact id for message header.
     * @return This builder instance.
     */
    public ArtifactRequestBuilder<T> operationGet(final URI requestedArtifact) {
        this.operation = Crud.RECEIVE;
        this.requestedArtifact = requestedArtifact;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageContainer<T> execute(final URI target)
            throws DapsTokenManagerException,
            ShaclValidatorException,
            SerializeException,
            ClaimsException,
            UnknownResponseException,
            SendMessageException,
            MultipartParseException,
            IOException,
            DeserializeException,
            RejectionException,
            UnexpectedPayloadException {
        //send ArtifactRequestMessage with settings:
        if (protocolType == null || operation == null) {
            final var errorMessage = String.format(
                    "Could not send Message, needed Fields are null: %s%s",
                    protocolType == null ? "protocolType is null! " : "",
                    operation == null ? "operation is null! " : ""
            );
            throw new SendMessageException(errorMessage);
        }
        switch (protocolType) {
            case IDSCP:
                throw new UnsupportedOperationException("Not yet implemented Protocol!");
            case LDP:
                throw new UnsupportedOperationException("Not yet implemented Protocol!");
            case MULTIPART:
                switch (operation) {
                    case RECEIVE:
                        //build and send artifact request message
                        final var message = requestTemplateProvider
                            .artifactRequestMessageTemplate(
                                    requestedArtifact)
                            .buildMessage();
                        return sendMultipart(target, message);
                    default:
                        throw new UnsupportedOperationException("Unsupported Operation!");
                }
            default:
                throw new UnsupportedOperationException("Unsupported Protocol!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArtifactRequestBuilder<T> useMultipart() {
        this.protocolType = ProtocolType.MULTIPART;
        return this;
    }
}