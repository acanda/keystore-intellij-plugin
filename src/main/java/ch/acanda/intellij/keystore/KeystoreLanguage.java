package ch.acanda.intellij.keystore;

import com.intellij.lang.Language;

public class KeystoreLanguage extends Language {

    public static final KeystoreLanguage INSTANCE = new KeystoreLanguage();

    private KeystoreLanguage() {
        super("Keystore");
    }

}
