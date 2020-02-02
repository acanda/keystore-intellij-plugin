package ch.acanda.intellij.keystore;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;


public class KeystoreFileType implements FileType {

    public static final KeystoreFileType INSTANCE = new KeystoreFileType();

    @NotNull
    @Override
    public String getName() {
        return "Keystore";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Java Keystore";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "jks";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.Nodes.Padlock;
    }

    @Override
    public boolean isBinary() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
        return null;
    }

}
