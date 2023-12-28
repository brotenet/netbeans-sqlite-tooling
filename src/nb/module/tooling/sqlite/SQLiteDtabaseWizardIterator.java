/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/instantiatingIterator.java to edit this template
 */
package nb.module.tooling.sqlite;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.util.NbBundle.Messages;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.netbeans.api.project.Project;

@TemplateRegistration(folder = "Other", displayName = "#SQLiteDtabaseWizardIterator_displayName", iconBase = "nb/module/tooling/sqlite/database.png", description = "sQLiteDtabase.html")
@Messages("SQLiteDtabaseWizardIterator_displayName=SQLite Dtabase")
public final class SQLiteDtabaseWizardIterator implements WizardDescriptor.InstantiatingIterator<WizardDescriptor> {

    private int index;

    private WizardDescriptor wizard;
    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
	if (panels == null) {
	    panels = new ArrayList<>();
	    panels.add(new SQLiteDtabaseWizardPanel1());
	    String[] steps = createSteps();
	    for (int i = 0; i < panels.size(); i++) {
		Component c = panels.get(i).getComponent();
		if (steps[i] == null) {
		    steps[i] = c.getName();
		}
		if (c instanceof JComponent) {
		    JComponent jc = (JComponent) c;
		    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
		    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
		    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
		    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
		    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
		}
	    }
	}
	return panels;
    }

    @Override
    public Set<?> instantiate() throws IOException {
	SQLiteDtabaseVisualPanel1 panel = (SQLiteDtabaseVisualPanel1) panels.get(0).getComponent();
	String path = panel.targetDir().getText().trim() + File.separator + panel.databaseName().getText().trim();
	if(!Files.exists(Paths.get(path))){
	    copyResourceToFile("/nb/module/tooling/sqlite/database.db", Paths.get(path));
	}else{
	    JOptionPane.showMessageDialog(null, "File '" + path + "' already exists.\nPlease use a different database name.", "Invalid user input", JOptionPane.ERROR_MESSAGE);
	    throw new IOException("File '" + path + "' already exists.");
	}
	return null;
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
	this.wizard = wizard;
	Project selectedProject = Templates.getProject(wizard);
	if (selectedProject != null) {
	    String projectPath = selectedProject.getProjectDirectory().getPath();
	    wizard.putProperty("projectPath", projectPath);
	}
	wizard.putProperty("targetPath", Templates.getTargetFolder(wizard).getPath());
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
	panels = null;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
	return getPanels().get(index);
    }

    @Override
    public String name() {
	return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
	return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
	return index > 0;
    }

    @Override
    public void nextPanel() {
	if (!hasNext()) {
	    throw new NoSuchElementException();
	}
	index++;
    }

    @Override
    public void previousPanel() {
	if (!hasPrevious()) {
	    throw new NoSuchElementException();
	}
	index--;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    
    private String[] createSteps() {
	String[] beforeSteps = (String[]) wizard.getProperty("WizardPanel_contentData");
	assert beforeSteps != null : "This wizard may only be used embedded in the template wizard";
	String[] res = new String[(beforeSteps.length - 1) + panels.size()];
	for (int i = 0; i < res.length; i++) {
	    if (i < (beforeSteps.length - 1)) {
		res[i] = beforeSteps[i];
	    } else {
		res[i] = panels.get(i - beforeSteps.length + 1).getComponent().getName();
	    }
	}
	return res;
    }

    private static void copyResourceToFile(String resourceName, Path destinationPath) throws IOException {
        // Open an InputStream for the resource file
        try (InputStream inputStream = SQLiteDtabaseWizardIterator.class.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourceName);
            }
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
