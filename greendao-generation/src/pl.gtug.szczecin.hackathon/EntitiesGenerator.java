package pl.gtug.szczecin.hackathon;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import pl.gtug.szczecin.hackathon.database.DbHelper;

/**
 * Created by Pawel Stawicki on 2/19/12 10:50 AM
 */
public class EntitiesGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "pl.gtug.szczecin.hackathon.database.generated");

        addTodoItem(schema);
        new DaoGenerator().generateAll(schema, "src");
    }

    private static void addTodoItem(Schema schema) {
        Entity location = schema.addEntity(DbHelper.LOCATION);
        location.addIdProperty();
        location.addDoubleProperty(DbHelper.LAT).notNull();
        location.addDoubleProperty(DbHelper.LON).notNull();

        Entity todoItem = schema.addEntity(DbHelper.TODO_ITEM);
        todoItem.addIdProperty();
        todoItem.addStringProperty(DbHelper.DESCRIPTION).notNull();
        todoItem.addBooleanProperty(DbHelper.IS_DONE).notNull();
        Property locationIdInTodoItem = todoItem.addLongProperty("locationId").getProperty();

        location.addToMany(todoItem, locationIdInTodoItem);
    }

}
