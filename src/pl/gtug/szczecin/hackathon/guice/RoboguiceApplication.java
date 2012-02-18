package pl.gtug.szczecin.hackathon.guice;

import com.google.inject.Module;
import pl.gtug.szczecin.hackathon.database.DbHelper;
import roboguice.application.RoboApplication;
import roboguice.config.AbstractAndroidModule;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class RoboguiceApplication extends RoboApplication {

    @Override
    protected void addApplicationModules(List<Module> modules){
       modules.add(new AbstractAndroidModule(){
         @Override
         protected void configure() {
           requestStaticInjection(DbHelper.class);
         }
       });
    }

}
