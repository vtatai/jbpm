/**
 * Copyright 2010 JBoss Inc
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

package org.jbpm.task.utils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.task.impl.model.AttachmentImpl;
import org.jbpm.task.impl.model.BooleanExpressionImpl;
import org.jbpm.task.impl.model.CommentImpl;
import org.jbpm.task.impl.model.DeadlineImpl;
import org.jbpm.task.impl.model.EmailNotificationImpl;
import org.jbpm.task.impl.model.EscalationImpl;
import org.jbpm.task.impl.model.GroupImpl;
import org.jbpm.task.impl.model.I18NTextImpl;
import org.jbpm.task.impl.model.NotificationImpl;
import org.jbpm.task.impl.model.ReassignmentImpl;
import org.jbpm.task.impl.model.UserImpl;
import org.kie.internal.task.api.model.Attachment;
import org.kie.internal.task.api.model.BooleanExpression;
import org.kie.internal.task.api.model.Comment;
import org.kie.internal.task.api.model.Deadline;
import org.kie.internal.task.api.model.Escalation;
import org.kie.internal.task.api.model.Group;
import org.kie.internal.task.api.model.I18NText;
import org.kie.internal.task.api.model.Notification;
import org.kie.internal.task.api.model.NotificationType;
import org.kie.internal.task.api.model.OrganizationalEntity;
import org.kie.internal.task.api.model.Reassignment;
import org.kie.internal.task.api.model.User;

public class CollectionUtils {
    
    public static boolean equals(List list1, List list2) {
        if ( list1 == null && list2 == null ) {
            // both are null
            return true;
        }
        
        if ( list1 == null || list2 == null ) {
            // we know both aren't null, so if one is null them obviously false
            return false;
        }        
        
        if ( list1.size() != list2.size() ) {
            return false;
        } 
        
        if ( list1.isEmpty() && list2.isEmpty() ) {
            return true;
        }
        
       
        for ( Object item1 : list1) {
            boolean exists = false;
            for ( Object item2 : list2 ) {
                if ( item1.equals( item2 )) {
                    exists = true;
                    break;
                }
            }  
            if ( !exists ) {
                return false;
            }
        }
        
        return true;
    }
    
    public static int hashCode(List list) {
        if ( list == null ) {
            return 0;
        }
        
        final int prime = 31;
        int result = 1;        
        for ( Iterator it = list.iterator(); it.hasNext(); ) {
            Object next = it.next();
            result = prime * result + ((next == null)? 0 : next.hashCode());
        }        
        return result;
    }       
    
    public static void writeCommentList(List<Comment> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( Comment item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<Comment> readCommentList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<Comment> list = new ArrayList<Comment>(size);
        for ( int i = 0; i < size; i++ ) {
            Comment item = new CommentImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }     
    
    
    public static void writeAttachmentList(List<Attachment> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( Attachment item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<Attachment> readAttachmentList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<Attachment> list = new ArrayList<Attachment>(size);
        for ( int i = 0; i < size; i++ ) {
            Attachment item = new AttachmentImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }      
    
    public static void writeBooleanExpressionList(List<BooleanExpression> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( BooleanExpression item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<BooleanExpression> readBooleanExpressionList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<BooleanExpression> list = new ArrayList<BooleanExpression>(size);
        for ( int i = 0; i < size; i++ ) {
            BooleanExpression item = new BooleanExpressionImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }     

    
    public static void writeNotificationList(List<Notification> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( Notification item : list ) {
            // item.getNotificationType().toString() is never null
            out.writeUTF( item.getNotificationType().toString() );
            item.writeExternal( out );
        }
    }    
    
    public static List<Notification> readNotificationList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<Notification> list = new ArrayList<Notification>(size);
        for ( int i = 0; i < size; i++ ) {
            Notification item = null;
            switch( NotificationType.valueOf(  in.readUTF() ) ) {
                case Default : {
                    item = new NotificationImpl();
                    break;
                }
                case Email : {
                    item = new EmailNotificationImpl();
                    break;
                }
            }
             
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }     
    
    
    public static void writeReassignmentList(List<Reassignment> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( Reassignment item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<Reassignment> readReassignmentList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<Reassignment> list = new ArrayList<Reassignment>(size);
        for ( int i = 0; i < size; i++ ) {
            Reassignment item = new ReassignmentImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }       
    
    public static void writeDeadlineList(List<Deadline> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( Deadline item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<Deadline> readDeadlinesList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<Deadline> list = new ArrayList<Deadline>(size);
        for ( int i = 0; i < size; i++ ) {
            Deadline item = new DeadlineImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }    
    
    public static void writeEscalationList(List<Escalation> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( Escalation item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<Escalation> readEscalationList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<Escalation> list = new ArrayList<Escalation>(size);
        for ( int i = 0; i < size; i++ ) {
            Escalation item = new EscalationImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }    
    
    public static void writeI18NTextList(List<I18NText> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( I18NText item : list ) {
            item.writeExternal( out );
        }
    }    
    
    public static List<I18NText> readI18NTextList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<I18NText> list = new ArrayList<I18NText>(size);
        for ( int i = 0; i < size; i++ ) {
            I18NText item = new I18NTextImpl();
            item.readExternal( in );
            list.add( item );
        }
        return list;
    }
    
    public static void writeOrganizationalEntityList(List<OrganizationalEntity> list, ObjectOutput out) throws IOException {
        out.writeInt( list.size() );
        for( OrganizationalEntity item : list ) {
            if ( item instanceof User ) {
                out.writeShort( 0 );
            } else {
                out.writeShort( 1 );
            }
            item.writeExternal( out );
        }
    }
    
    public static List<OrganizationalEntity> readOrganizationalEntityList(ObjectInput in) throws IOException, ClassNotFoundException  {
        int size = in.readInt();
        List<OrganizationalEntity> list = new ArrayList<OrganizationalEntity>(size);
        for ( int i = 0; i < size; i++ ) {
            short type = in.readShort();
            if ( type == 0 ) {
                User user = new UserImpl();
                user.readExternal( in );
                list.add( user ); 
            } else {
                Group group = new GroupImpl();
                group.readExternal( in );
                list.add( group );
            }
        }
        return list;
    }    
}
