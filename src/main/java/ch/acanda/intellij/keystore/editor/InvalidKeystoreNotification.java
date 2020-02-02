package ch.acanda.intellij.keystore.editor;

import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotifications;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InvalidKeystoreNotification extends EditorNotifications.Provider<EditorNotificationPanel> implements DumbAware {

    public static final Key<EditorNotificationPanel> KEY = Key.create(InvalidKeystoreNotification.class.getName());

    @NotNull
    @Override
    public Key<EditorNotificationPanel> getKey() {
        return KEY;
    }

    @Nullable
    @Override
    public EditorNotificationPanel createNotificationPanel(@NotNull VirtualFile file, @NotNull FileEditor editor, @NotNull Project project) {
        if (editor instanceof KeystoreEditor) {
            final KeystoreEditor keystoreEditor = (KeystoreEditor) editor;
            if (!keystoreEditor.isValidKeystore()) {
                final EditorNotificationPanel panel = new EditorNotificationPanel();
                panel.setText(file.getName() + " is not a keystore file.");
                return panel;
            }
        }
        return null;
    }

}
