package ch.acanda.intellij.keystore.editor;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class KeystoreModel {

    private final List<CertModel> certs;

    @Data
    @Builder
    public static class CertModel {
        private final String alias;
        private final Date creationDate;
        private final String subject;
        private final String issuer;
        private final Date validFrom;
        private final Date validUntil;
        private final byte[] fingerprint;
    }

}
