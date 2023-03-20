package edu.pdx.cs410J.kgujral;

public class HelpTopicHelper {

    private static String[] topics = new String[] {
            "README if you're new here",
            "How to add a new airline",
            "How to add a new flight to an existing airline"};

    private static String[] topicContents = new String[]{
            "===============================================================\n" +
                    "CS501 Project 6:: A Small Device Application for an Airline\n" +
                    "- Kaushambi Gujral\n" +
                    "\n" +
                    "This app helps the user keep track of airlines and their flights.\n" +
                    "The user can add new airlines and flights using the '+' button on the bottom right menu. " +
                    "All the airlines added to the system will be displayed on the home screen. " +
                    "Upon selecting an airline from the list, all the flights under the airline will be prettified and displayed\n" +
                    "The user can search for flights using the search bar on the home screen. \n"+
                    "---------------------------------------------------------------\n" +
                    "An airline needs the below data:\n" +
                    "* name\n" +
                    "a flight needs the below data:\n" +
                    "* flight-number (is a whole number eg: 5678)\n" +
                    "* departure airport code (contains 3 letters eg: PDX and is a valid, real world airport code)\n" +
                    "* departure date and time (12 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 08:22 am)\n" +
                    "* arrival airport code (contains 3 letters eg: SEA and is a valid, real world airport code)\n" +
                    "* arrival date and time (12 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 18:22 pm)",
            "To add a new airline, press the '+' button in the bottom left menu. You should see a screen with a form. Fill up all the details and you're good to go!",
            "To add a new flight, press the '+' button in the bottom left menu. You should see a screen with a form. " +
                    "You may choose from an already existing airline or add a new one." +
                    "Fill up all the details and you're good to go!"};

    public static HelpTopic[] getHelpTopics() {
        HelpTopic[] helpTopics = new HelpTopic[topics.length];
        for(int i=0; i< topics.length; i++){
            helpTopics[i] = new HelpTopic(topics[i], topicContents[i]);
        }
        return helpTopics;
    }
}

class HelpTopic{
    public HelpTopic(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    private String topic;
    private String content;

    public String getTopic(){
        return topic;
    }

    public String getContent() {
        return content;
    }
}
