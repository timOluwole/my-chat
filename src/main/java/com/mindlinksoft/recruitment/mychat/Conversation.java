package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private final String name;

    /**
     * The messages in the conversation.
     */
    private final Collection<Message> messages;

    /**
     * NEW FEATURE: The messaging activity of the users in the conversation.
     */
    private List<SenderMessagePairing> activity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    public void trackActivity() {
        // cannot track activity on null messages
        if (messages == null) {
            return;
        }

        activity = new ArrayList<>();

        int i;
        boolean senderActivityAlreadyTracked;

        for (Message message : messages) {
            senderActivityAlreadyTracked = false;
            for (i = 0; i < activity.size(); i++) {
                // check if sender has already been tracked
                if (message.getSenderId().equals(activity.get(i).getName())) {
                    senderActivityAlreadyTracked = true;
                    break;
                }
            }
            // if the sender has not already been tracked, add to tracked activity
            if (!senderActivityAlreadyTracked) {
                activity.add(new SenderMessagePairing(message.getSenderId()));
            }
            // increment sender's message count
            activity.get(i).incrementCount();
        }
    }

    public void sortActivity() {
        // cannot sort null activity
        if (activity == null) {
            return;
        }
        // sort senders in descending order of message count
        activity.sort((a1, a2) -> (a2.count - a1.count));
    }

    // - Getters and Setters (changed the class variables to private)

    public String getName() {
        return name;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public List<SenderMessagePairing> getActivity() {
        return activity;
    }

    class SenderMessagePairing {
        private String name;
        private int count;

        public SenderMessagePairing(String name) {
            this.name = name;
            this.count = 0;
        }

        public void incrementCount() {
            this.count++;
        }

        public String getName() {
            return this.name;
        }

        public int getCount() {
            return count;
        }
    }
}
