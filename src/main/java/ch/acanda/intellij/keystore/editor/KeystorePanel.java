package ch.acanda.intellij.keystore.editor;

import com.intellij.ui.treeStructure.Tree;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class KeystorePanel {

    private JPanel panel;
    private Tree tree;

    public JComponent getComponent() {
        return panel;
    }

    public void dispose() {
    }

    public void setAliases(final List<String> aliases) {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        aliases.stream().sorted().forEach(alias -> root.add(new DefaultMutableTreeNode(alias)));
        tree.setModel(new DefaultTreeModel(root));
    }

}
