package sai.application.betch.jobscheduler;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/18/17.
 */

@Module
public class JobsModule {

    @Provides NotificationJobMVP.Presenter provideNotificationJobPresenter(NotificationJobMVP.Model model) {
        return new NotificationJobPresenter(model);
    }

    @Provides NotificationJobMVP.Model provideNotificationJobModel(IRepository repository) {
        return new NotificationJobModel(repository);
    }
}
