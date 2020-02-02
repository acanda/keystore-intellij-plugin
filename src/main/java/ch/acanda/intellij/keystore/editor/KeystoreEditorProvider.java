package ch.acanda.intellij.keystore.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class KeystoreEditorProvider implements FileEditorProvider {

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        return "jks".equalsIgnoreCase(file.getExtension());
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        return new KeystoreEditor(file);
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "KeystoreEditor";
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR;
    }

}
