/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/templateDataObjectAnno.java to edit this template
 */
package nb.module.tooling.sqlite;

import java.io.IOException;
import org.netbeans.api.actions.Openable;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

@MIMEResolver.ExtensionRegistration(
        displayName = "SQLite Database",
        mimeType = "application/vnd.sqlite3",
        extension = {"db", "database", "sqlite", "sqlite3"}
)
@DataObject.Registration(
        mimeType = "application/vnd.sqlite3",
        iconBase = "nb/module/tooling/sqlite/database.png",
        displayName = "SQLite Database",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/application/vnd.sqlite3/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class SQLiteDataObject extends MultiDataObject implements Openable {

    private FileObject file_object;
    private MultiFileLoader file_loader;

    public SQLiteDataObject(FileObject file_object, MultiFileLoader file_loader) throws DataObjectExistsException, IOException {
        super(file_object, file_loader);
        this.file_object = file_object;
        this.file_loader = file_loader;
    }

    @Override
    public void open() {
        try {
            String sqlite_studio_path = NbPreferences.forModule(SQLitePanel.class).get("sqlite_studio_path", null);
            if (!(sqlite_studio_path.trim().length() > 0)) {
                sqlite_studio_path = null;
            }
            if (sqlite_studio_path != null) {
                try {
                    new ProcessBuilder(sqlite_studio_path, this.file_object.getPath()).start();
                } catch (IOException exception) {
                    NotifyDescriptor descriptor = new NotifyDescriptor.Message("ERROR: " + exception.getMessage() + "\nA valid SQLite Studio path was not set.\nPlease provide a valid path in 'Tools > Options > SQLite [section]'\nfor this feature to be made available.", NotifyDescriptor.ERROR_MESSAGE);
                    DialogDisplayer.getDefault().notify(descriptor);
                }
            } else {
                NotifyDescriptor descriptor = new NotifyDescriptor.Message("SQLite Studio path was not set.\nPlease provide a valid path in 'Tools > Options > SQLite [section]'\nfor this feature to be made available.", NotifyDescriptor.WARNING_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
        } catch (Exception exception) {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message("ERROR: " + exception.getMessage() + "\nA valid SQLite Studio path was not set.\nPlease provide a valid path in 'Tools > Options > SQLite [section]'\nfor this feature to be made available.", NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
        }
    }
}
