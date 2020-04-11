package ch.acanda.intellij.keystore.editor;

import ch.acanda.intellij.keystore.editor.KeystoreModel.CertModel;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class KeystorePanel {

    private JPanel panel;
    private Tree tree;
    private CertificatePanel certificatePanel;
    private JPanel detailPanel;
    private KeystoreModel model;

    public KeystorePanel() {
        tree.addTreeSelectionListener(event -> {
            final DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
            final String alias = (String) node.getUserObject();
            model.getCerts().stream()
                    .filter(c -> c.getAlias().equals(alias))
                    .findAny()
                    .ifPresent(certificatePanel::setModel);
        });
    }

    public JComponent getComponent() {
        return panel;
    }

    public void dispose() {
    }

    public void setModel(final KeystoreModel model) {
        this.model = model;
        setAliases(model.getCerts().stream().map(CertModel::getAlias).collect(toList()));
    }

    private void setAliases(final List<String> aliases) {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        aliases.stream().sorted().forEach(alias -> root.add(new DefaultMutableTreeNode(alias)));
        tree.setModel(new DefaultTreeModel(root));
    }

    private void createUIComponents() {
        certificatePanel = new CertificatePanel();
        detailPanel = certificatePanel.getPanel();
    }

}
