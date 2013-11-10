/*
 * Copyright 2013 Rimero Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rimerosolutions.gorm.utils

import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor
import org.codehaus.groovy.grails.orm.hibernate.events.PatchedDefaultFlushEventListener
import org.hibernate.event.SaveOrUpdateEventListener
import org.hibernate.event.SaveOrUpdateEvent
import org.hibernate.HibernateException

/**
 * Enable GORM interceptors in standalone mode
 */
class GormInterceptorsHelper {

    static void initializeInterceptors(sessionFactory) {
        def eventListeners = sessionFactory.eventListeners
        def interceptorsMap = populateGormInteceptorsMap()

        interceptorsMap.each { k, v ->
            augmentEventListenerList(eventListeners, k, v)
        }

        eventListeners.validate()
    }

    private static Map populateGormInteceptorsMap() {
        [
            "flushEventListeners": [new PatchedDefaultFlushEventListener()],
            "preLoadEventListeners": [new ClosureEventTriggeringInterceptor()],
            "postLoadEventListeners": [new ClosureEventTriggeringInterceptor()],
            "saveEventListeners": [new ClosureEventTriggeringInterceptor(), new AutoTimestampInterceptor()],
            "saveOrUpdateEventListeners": [new ClosureEventTriggeringInterceptor(), new AutoTimestampInterceptor()],
            "postInsertEventListeners": [new ClosureEventTriggeringInterceptor()],
            "preUpdateEventListeners": [new ClosureEventTriggeringInterceptor()],
            "postUpdateEventListeners": [new ClosureEventTriggeringInterceptor()],
            "preDeleteEventListeners": [new ClosureEventTriggeringInterceptor()],
            "postDeleteEventListeners": [new ClosureEventTriggeringInterceptor()]
        ]
    }

    private static void augmentEventListenerList(eventListeners, eventListenersProperty, interceptorList) {
        def newListeners  = (eventListeners."${eventListenersProperty}" as List) + interceptorList
        eventListeners."${eventListenersProperty}" = newListeners
    }

    private static class AutoTimestampInterceptor implements SaveOrUpdateEventListener {
        public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
            def entity = event.entity

            // Last Updated seems ok... so skipping
            if (entity.hasProperty('dateCreated')) {
                setDate(entity, 'dateCreated')
            }
        }
    }

    private static void setDate(entity, propertyName) {
        if (entity.hasProperty(propertyName)) {
            def propertyType = entity.class.metaClass.properties.find { it.name == propertyName }.type
            if (propertyType == Date) {
                if (!(entity."${propertyName}")) {
                    entity."${propertyName}" = new Date()
                }
            }
        }
    }

}