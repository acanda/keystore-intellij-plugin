package ch.acanda.intellij.keystore.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotifications;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collections;

public class KeystoreEditor implements FileEditor {

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
            panel.setAliases(Collections.list(keyStore.aliases()));

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            isValidKeystore = false;
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
