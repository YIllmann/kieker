package mySimpleKiekerAspectJExample.probe;

/*
 * ==================LICENCE=========================
 * Copyright 2006-2009 Kieker Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ==================================================
 */

import kieker.monitoring.core.MonitoringController;
import kieker.monitoring.probe.IMonitoringProbe;
import mySimpleKiekerAspectJExample.annotation.MyRTProbe;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import mySimpleKiekerAspectJExample.record.MyRTMonitoringRecord;

/** @author Andre van Hoorn
 */
@Aspect
public class MyRTMonitoringProbe implements IMonitoringProbe {

    protected static final MonitoringController CTRL = MonitoringController.getInstance();

    @Around(value = "execution(@mySimpleKiekerAspectJExample.annotation.MyRTProbe * *.*(..))")
    public Object probe(ProceedingJoinPoint j) throws Throwable {
        MyRTMonitoringRecord record = new MyRTMonitoringRecord();
        record.component = j.getSignature().getDeclaringTypeName();
        record.service = j.getSignature().getName();
        Object retval; 
        long tin = CTRL.currentTimeNanos();
        try {
            retval = j.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            record.rt = CTRL.currentTimeNanos() - tin;
            CTRL.newMonitoringRecord(record);
        }
        return retval;
    }
}
