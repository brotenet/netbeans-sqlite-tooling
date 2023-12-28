/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/wizardPanel.java to edit this template
 */
package nb.module.tooling.sqlite;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SQLiteDtabaseWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor> {

    private SQLiteDtabaseVisualPanel1 component;

    @Override
    public SQLiteDtabaseVisualPanel1 getComponent() {
	if (component == null) {
	    component = new SQLiteDtabaseVisualPanel1();
	}
	return component;
    }

    @Override
    public HelpCtx getHelp() {
	return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
	return true;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wizard) {
	component.targetDir().setText(String.valueOf(wizard.getProperty("targetPath")));
	//component.putClientProperty("targetPath", wizard.getProperty("targetPath"));
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
	// use wiz.putProperty to remember current panel state
    }

}
