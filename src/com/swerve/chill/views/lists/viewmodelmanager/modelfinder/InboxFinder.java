package com.swerve.chill.views.lists.viewmodelmanager.modelfinder;

import com.swerve.chill.model.remote.Message;
import com.swerve.chill.views.contracts.ModelFinder;
import com.swerve.chill.views.contracts.ServiceAccessor;

import java.util.Collection;

public class InboxFinder implements ModelFinder <Message>{
    public InboxFinder() {

    }

    @Override
    public Collection<Message> findModels(final ServiceAccessor serviceAccessor) {
        return serviceAccessor.getInbox();
    }
}
