package ch.acanda.intellij.keystore.editor;

import ch.acanda.intellij.keystore.editor.KeystoreModel.CertModel;
import com.intellij.ui.components.JBTextField;

import javax.swing.JPanel;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;

public class CertificatePanel {

    private static final byte[] HEX_ENCODING = {
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
            (byte) '8', (byte) '9', (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F'
    };

    private JPanel panel;
    private JBTextField aliasField;
    private JBTextField fingerprintField;
    private JBTextField creationField;
    private JBTextField validFromField;
    private JBTextField validUntilField;
    private JBTextField subjectField;
    private JBTextField issuerField;

    public JPanel getPanel() {
        return panel;
    }

    public void setModel(final CertModel model) {
        aliasField.setText(model.getAlias());
        subjectField.setText(model.getSubject());
        issuerField.setText(model.getIssuer());
        creationField.setText(format(model.getCreationDate()));
        validFromField.setText(format(model.getValidFrom()));
        validUntilField.setText(format(model.getValidUntil()));
        fingerprintField.setText(format(model.getFingerprint()));
    }

    private String format(final Date date) {
        if (date == null) {
            return null;
        }
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG).format(date);
    }

    public String format(final byte[] bytes) {
        final byte[] chars = new byte[bytes.length * 3 - 1];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            if (j != 0) {
                chars[j++] = ':';
            }
            final int b = bytes[i] & 0xff;
            chars[j++] = HEX_ENCODING[b >> 4];
            chars[j++] = HEX_ENCODING[b & 0x0f];
        }
        return new String(chars, StandardCharsets.US_ASCII);
    }

}
