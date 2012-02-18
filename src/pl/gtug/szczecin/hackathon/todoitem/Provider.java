package pl.gtug.szczecin.hackathon.todoitem;

import com.google.inject.Singleton;
import com.google.inject.internal.Lists;

import java.util.List;

/**
 * Created by Pawel Stawicki on 2/18/12 3:51 PM
 */
@Singleton
public class Provider {

    public List<TodoItem> getItems() {
        return Lists.newArrayList(
                        new TodoItem("asdf"),
                        new TodoItem("asdfasdf"),
                        new TodoItem("sdfasgherghwe Dupa"));
    }

}
