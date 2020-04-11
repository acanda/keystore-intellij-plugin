package ch.acanda.intellij.keystore.editor;

import ch.acanda.intellij.keystore.editor.KeystoreModel.CertModel.CertModelBuilder;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class KeystoreEditor implements FileEditor, DumbAware {

    private final VirtualFile file;
    private final KeystorePanel panel;
    private boolean isValidKeystore;

    public KeystoreEditor(final VirtualFile file) {
        this.file = file;
        panel = new KeystorePanel();
        final KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance("jks");
            keyStore.load(file.getInputStream(), null);
            isValidKeystore = true;
            final ArrayList<String> aliases = Collections.list(keyStore.aliases());
            List<KeystoreModel.CertModel> certModels = new ArrayList<>(aliases.size());
            for (final String alias : aliases) {
                final Certificate certificate = keyStore.getCertificate(alias);
                final CertModelBuilder builder = KeystoreModel.CertModel.builder()
                        .alias(alias)
                        .creationDate(keyStore.getCreationDate(alias));
                if (certificate instanceof X509Certificate) {
                    final X509Certificate x509cert = (X509Certificate) certificate;
                    builder.subject(x509cert.getSubjectDN().toString());
                    builder.issuer(x509cert.getIssuerDN().toString());
                    builder.validFrom(x509cert.getNotBefore());
                    builder.validUntil(x509cert.getNotAfter());
                    builder.fingerprint(sha256(x509cert.getEncoded()));
                }
                certModels.add(builder.build());
            }
            panel.setModel(KeystoreModel.builder().certs(certModels).build());


            for (String alias : aliases) {
                System.out.println(alias);
                final Certificate certificate = keyStore.getCertificate(alias);
                System.out.println("  Creation Date: " + keyStore.getCreationDate(alias));
                System.out.println("  Cert Class: " + certificate.getClass().getCanonicalName());
                System.out.println("  Cert Type: " + certificate.getType());
                if (certificate instanceof X509Certificate) {
                    X509Certificate x509cert = (X509Certificate) certificate;
                    System.out.println(" Cert Serial: " + x509cert.getSerialNumber().toString(16));
                }
                final KeyStore.Entry entry = keyStore.getEntry(alias, null);
                System.out.println("  Entry Class: " + entry.getClass().getCanonicalName());
                final Set<KeyStore.Entry.Attribute> attributes = entry.getAttributes();
                if (!attributes.isEmpty()) {
                    System.out.println("  Attributes:");
                    for (KeyStore.Entry.Attribute attribute : attributes) {
                        System.out.println("    " + attribute.getName() + ": " + attribute.getValue());
                    }
                }
            }

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableEntryException e) {
            isValidKeystore = false;
        }
    }

    private byte[] sha256(final byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidKeystore() {
        return isValidKeystore;
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return panel.getComponent();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public void setState(@NotNull FileEditorState state) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return file.isValid();
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {
        panel.dispose();
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }
}
