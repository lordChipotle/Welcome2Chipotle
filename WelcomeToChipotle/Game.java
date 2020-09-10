/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<String,Room> hashmaprooms = new HashMap<String,Room>();
    private HashMap<String,ArrayList<Item>> hashmapstorageitems = new HashMap<String,ArrayList<Item>>();
    private HashMap<String,Item> hashmapitems = new HashMap<String,Item>();
    private HashMap<String,Quest>quests = new HashMap<String,Quest>();
    private HashMap<String,Chipotlian>chipotlians = new HashMap<String,Chipotlian>();
    
    
    private ArrayList<Room> rooms;
    private ArrayList<Room> markedRooms = new ArrayList<>();
    private ArrayList<Item>itemsInCurrentLoc ;
    private ArrayList<Item>nonNPCitems;
    private ArrayList<Item>NPCitems;
    private ArrayList<Item>allitems;
    private ArrayList<Item> found= new ArrayList<>();
    private ArrayList<Docs> misc = new ArrayList<>();
    private ArrayList<Item> chipsack = new ArrayList<>();
    private ArrayList<Docs>alldocs = new ArrayList<>();
    private ArrayList<Docs>notebook = new ArrayList<>();
    private ArrayList<Room>area1;
    private ArrayList<Room>area2;
    private ArrayList<Room>area3;
    private ArrayList<String>questcharacternames = new ArrayList<>(Arrays.asList("Steve","Lord", "Freeman","DatPriest","Angel"));
    private ArrayList<Quest> questList;
    private ArrayList<Item>eatenavo = new ArrayList<>();
    
    
    private int currentObjective = 0;
    private double chipsacklimit = 15.0;
    private boolean guacseapass = false; 
    private boolean salsapass = false;
    private boolean chipotlepass = false;
    private boolean avoinbelly = false;
    private boolean present = false;
    private boolean chipotlemet = false;
    private boolean finished = false;
    private int nutrition = 80;
    private boolean mapUsed = false; //indicate if the map is used, if used, next wiggle command clear screen and erase the map
    private boolean wilderhasPep = false;
    private boolean hawkinghasPep = false;
    private boolean bowiehasPep = false;
    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
    	createItems();
        createRooms();
        createChipotlians();
        
        createQuestCharacters();
        createDocs();
        createQuests();
        parser = new Parser();
    }
    private void createQuests() {
    	Quest quest0,quest1,quest2,quest3,quest4,quest5;
    	quest0 = new Quest("Welcome to Your Afterlife","\n*Feel free to wander around adjust your new life, oh and find some Avocados ");
    	quest1 = new Quest("I'm an explorer","\n*Wiggle west and explore the guac sea");

    	quest2 = new Quest("Another continent","\n*Darn the sea, you can't swim! Find Steve");
    	quest3 = new Quest("Hot!Hot!HOT!WTH","\n*Can't believe they have desert made of salsa in heaven, \nAs a new 'immigrant' to heaven, your skin is yet still very vulnerable to this hotness. \nFind Morgan Freeman and ask for helps");
    	quest4 = new Quest("Crap, the guac is spoiling","\n*Guac in Chipotle is spoiling due to recent drop of civilians'faith, \nFind the missing priest and bring him to monastery.");
    	quest5 = new Quest("Let there be light","\n*Figure out how you died on earth, collect evidence and find Lord Chipotle at his palace");
    	//put all quests in hashmap for future access from other classes and methods
    	quests.put("quest0", quest0);
    	quests.put("quest1", quest1);
    	quests.put("quest2", quest2);
    	quests.put("quest3", quest3);
    	quests.put("quest4", quest4);
    	quests.put("quest5", quest5);
    	
    	//put all quests in Array list for future looping from other classes and methods

    	questList = new ArrayList<>(Arrays.asList(quest0,quest1,quest2,quest3,quest4,quest5));



    }
    /**
     * Create all the rooms and link their exits together.
     */
    
    private void createRooms()
    {
        Room entrance, guacswamp,guacsea, guachub1,guachub2,guachub3,tortilla, 
        	salsa, sofrita,farm,church,burrito,taco,sourcream,denver,mild,medium,hot,extraguac,chipotle;
        Room beanmine; //magic teleport room
       
        
        Random randloc = new Random(); 
        
        // create the rooms
        entrance = new Room("at the gate entrance of Chipotle \n damn, this place is actually stunning");

        guacswamp = new Room("in the Guac Swamp");
        guacsea = new Room("on a bowl, in the middle of Guac Sea");
        guachub1 = new Room("at Guac Hub I, a port for departure towards Guac Sea. \n This a hub where all the famous models and hollywood stars live");
        guachub2 = new Room("at Guac Hub II, a port for departure towards Guac Sea.\n A hub where all the schnitzels chefs from all over the world live");
        guachub3 = new Room("at Guac Hub III, a port for departure towards Guac Sea.\n A hub where all the football (not soccer) players live");
        tortilla = new Room("in the Tortilla Village");
        salsa= new Room("in the Dry Salsa Tribes");
        sofrita = new Room("in the Sofrita City");
        farm = new Room("at the Cow Dump Farm Town");
        church = new Room("at the Monastery");
        burrito = new Room("on the Burrito Coast");
        taco = new Room("in the Taco Dessert");
        sourcream = new Room("in a Sour Cream State");
        denver = new Room("in Denver, CO");
        mild = new Room("in the Mild Salsa Resort");
        medium = new Room("in the Medium Salsa Savana");
        hot = new Room("in the Hot Salsa Dessert");
        extraguac = new Room("in the ExtraGuac Province, the Holy Land ");
        chipotle = new Room("in the Sacrosanct Land of His Highness, Lord Chipotle");
        beanmine = new Room("You fell into a mine of magic beans and magic brown rice sand.\n The power of magic is has sprung you to some random place ");
        //put rooms in the hashmap
        
        hashmaprooms.put("entrance", entrance);
        hashmaprooms.put("guacswamp", guacswamp);
        hashmaprooms.put("guacsea", guacsea);
        hashmaprooms.put("guahub1", guachub1);
        hashmaprooms.put("guachub2", guachub2);
        hashmaprooms.put("guachub3", guachub3);
        hashmaprooms.put("tortilla", tortilla);
        hashmaprooms.put("salsa", salsa);
        hashmaprooms.put("sofrita", sofrita);
        hashmaprooms.put("farm", farm);
        hashmaprooms.put("church", church);
        hashmaprooms.put("burrito", burrito);
        hashmaprooms.put("taco", taco);
        hashmaprooms.put("sourcream", sourcream);
        hashmaprooms.put("denver", denver);
        hashmaprooms.put("mild", mild);
        hashmaprooms.put("medium", medium);
        hashmaprooms.put("hot", hot);
        hashmaprooms.put("extraguac", extraguac);
       
        hashmaprooms.put("chipotle", chipotle);
        hashmaprooms.put("beanmine", beanmine);
        //assign storage to rooms in a hashmap
        hashmapstorageitems.put("entrance", entrance.getStorage(nonNPCitems));
        hashmapstorageitems.put("guacswamp", guacswamp.getStorage(nonNPCitems));
        hashmapstorageitems.put("guacsea", guacsea.getStorage(nonNPCitems));
        hashmapstorageitems.put("guahub1", guachub1.getStorage(nonNPCitems));
        hashmapstorageitems.put("guachub2", guachub2.getStorage(nonNPCitems));
        hashmapstorageitems.put("guachub3", guachub3.getStorage(nonNPCitems));
        hashmapstorageitems.put("tortilla", tortilla.getStorage(nonNPCitems));
        hashmapstorageitems.put("salsa", salsa.getStorage(nonNPCitems));
        hashmapstorageitems.put("sofrita", sofrita.getStorage(nonNPCitems));
        hashmapstorageitems.put("farm", farm.getStorage(nonNPCitems));
        hashmapstorageitems.put("church", church.getStorage(nonNPCitems));
        hashmapstorageitems.put("burrito", burrito.getStorage(nonNPCitems));
        hashmapstorageitems.put("taco", taco.getStorage(nonNPCitems));
        hashmapstorageitems.put("sourcream", sourcream.getStorage(nonNPCitems));
        hashmapstorageitems.put("denver", denver.getStorage(nonNPCitems));
        hashmapstorageitems.put("mild", mild.getStorage(nonNPCitems));
        hashmapstorageitems.put("medium", medium.getStorage(nonNPCitems));
        hashmapstorageitems.put("hot", hot.getStorage(nonNPCitems));
        hashmapstorageitems.put("extraguac", extraguac.getStorage(nonNPCitems));
        hashmapstorageitems.put("chipotle", chipotle.getStorage(nonNPCitems));

        //create an array list for all room objects in the game for future access
        rooms = new ArrayList<>(Arrays.asList(entrance, guacswamp,guacsea, guachub1,guachub2,guachub3,tortilla, 
        	salsa, sofrita,farm,church,burrito,taco,sourcream,denver,mild,medium,hot,extraguac,chipotle)); //a list for later use
        
        
        
        
        
        // initialize room exits
        entrance.setExit("east", sofrita);
        entrance.setExit("south", guacswamp);
        entrance.setExit("west", guacsea);
        entrance.setExit("north", tortilla);

        guacswamp.setExit("west", guacsea);
        guacswamp.setExit("north", entrance);
        guacswamp.setExit("south", guachub3);
        

        guacsea.setExit("east", guachub3);
        guacsea.setExit("west",guachub1);
        guacsea.setExit("south", guachub2);
        
        guachub1.setExit("south", mild);
        guachub1.setExit("east", guacsea);
        guachub1.setExit("north", extraguac);
        guachub1.setExit("west", taco);
        
        guachub2.setExit("north", mild);
        guachub2.setExit("west", hot);
        guachub2.setExit("east", guacsea);

        
        guachub3.setExit("west", guacsea);
        guachub3.setExit("north", guacswamp);

        tortilla.setExit("south", entrance);
        tortilla.setExit("north", salsa);
        tortilla.setExit("west", burrito);
        tortilla.setExit("east", beanmine);
        
        salsa.setExit("east", farm);
        salsa.setExit("west", beanmine);
        salsa.setExit("north", beanmine);
        salsa.setExit("south", tortilla);

        church.setExit("south", sofrita);
        church.setExit("west", farm);

        sofrita.setExit("west", entrance);
        sofrita.setExit("north", church);
        
        farm.setExit("east", church);
        farm.setExit("west", salsa);
        farm.setExit("south", beanmine);

        burrito.setExit("south", denver);
        burrito.setExit("east", tortilla);
        
        taco.setExit("east", mild);
        taco.setExit("north", guachub1);
        taco.setExit("south", medium);
        taco.setExit("west", sourcream);
        
        denver.setExit("east", burrito);
        denver.setExit("west", guacsea);
        
        mild.setExit("north", guachub1);
        mild.setExit("south", guachub2);
        mild.setExit("west", taco);
        
        
        medium.setExit("north", mild);
        medium.setExit("south", hot);

        hot.setExit("north", medium);
        hot.setExit("east", guachub2);
        
        sourcream.setExit("south", medium);
        sourcream.setExit("east",taco);
        sourcream.setExit("north", extraguac);

        extraguac.setExit("south", sourcream);
        extraguac.setExit("east", chipotle);
        
        chipotle.setExit("west", extraguac);
        currentRoom = entrance;  // start game at the entrance of your Afterlife
        String name = getHashMapRoomsKey(currentRoom);
        
        //array lists for random room generation when fell into beanmines, maps are limited by the objective level, i.e., some maps are not accessible even by falling into the beanmines
        area1 = new ArrayList<Room>(Arrays.asList(entrance,guacswamp,burrito,denver,guachub3,sofrita,tortilla,church,farm));
        area2 = new ArrayList<Room>(Arrays.asList(entrance,guacswamp,burrito,denver,guachub3,sofrita,tortilla,church,farm,guachub2,guachub1,taco,sourcream,extraguac));
        area3 = new ArrayList<Room>(Arrays.asList(entrance,guacswamp,burrito,denver,guachub3,sofrita,tortilla,church,farm,guachub2,guachub1,taco,sourcream,extraguac,mild,medium,hot,salsa));
        

 
        itemsInCurrentLoc = hashmapstorageitems.get(name);// get current location's items
        
        }
    private void createChipotlians() {
    	//create normal, non-quest-related NPC
    	Chipotlian stanlee,bowie,randomdude,hawking,wilder;
    	Room stanleeroom,bowieroom,randomduderoom,hawkingroom,wilderroom;
    	Random location = new Random();
    	//randomly assigned rooms so that these NPCs are constantly moving
    	stanleeroom = rooms.get(location.nextInt(20));
    	stanlee = new Chipotlian("Stan","Howdy! You seems lost! \n If your looking for someone, talk to Hawking if you get a chance to meet him \n \n His brilliant brain can track everyone in Chipotle, \n although you do need a Doctor Pepper in exchange for his help \n nuff said! Good luck, son! ",stanleeroom);
    	stanlee.setThank("Excelsior! Thanks a lot, you are HULK SMASH! Always remember,  \n \"With great power, comes great responsibility!\"");
    	bowieroom = rooms.get(location.nextInt(20));
    	//create their initial conversation and end conversations when certain events are triggered
    	bowie = new Chipotlian("Bowie","Is there life on Maaaarsss, oh oh oh\n (guitar strumming) \n \n (still strumming) \n\n Bowie: \n Oh hey mate didn't see you there, you seems lost \n (tell the story) \n Sorry to hear that mate, I can certainly help \n but i'll have to need some Doctor Pepper for that",bowieroom);
    	bowie.setThank("Thanks a million mate! Let's dance, put on your red shoes and dance the blues~ \n\n (dance with david bowie)");
    	randomduderoom = rooms.get(location.nextInt(20));
    	randomdude = new Chipotlian("Dude","howdy, i'm a random dude, im very random, \n u can ask me anything but nothing. \n if ur starving, i can provide u some burrito bread. \n if u have troubles, welp good luck",randomduderoom);
    	randomdude.setThank("thanks homie");
    	hawkingroom = rooms.get(location.nextInt(20));
    	hawking = new Chipotlian("Hawking","",hawkingroom);
    	hawking.setThank("Thanks, young adventurist. \n The probability of me bidding u a last farewell now is somewhat significant \n but before you go, \n remember to look up at the stars and not down at your feet, \n try to make sense of what you see \n and wonder about what makes the universe exist. \n \n Be curious, and farewell.");
    	wilderroom = rooms.get(location.nextInt(20));
    	wilder = new Chipotlian("Wonka","Hello my friend, you look lost, consume this piece of chocolate and come with me\n \n (eat chocolate) \n in the next couple seconds you'll be in a world of pure imagination. \n\n (the world is distorting)\n\n (your in a chocolate factory) \n\n now tell me how may i help you today? ",wilderroom);
    	wilder.setThank("Thank you my friend, always remember, we are the music makers, and we are the dreamers of dreams");
    	//a hashmap of movable NPCs for objects giving
        chipotlians.put("Stan", stanlee);
        chipotlians.put("Bowie", bowie);
        chipotlians.put("Dude", randomdude);
        chipotlians.put("Hawking", hawking);
        chipotlians.put("Wonka", wilder);

    	
    }
    private void createQuestCharacters() {
    	//create Quest-related NPCs
    	Chipotlian steve,angel, morganfreeman,priest,lord;
    	steve = new Chipotlian("Steve","Hi, Im Steve Ells, founder of restaurant Chipotle Mexican Grills, welcome to Denver, Colorado \n you are in the first ever chipotle restaurant established in the country \n and guess what this is the only place on earth, \n where you can be spiritually present on earth, \n without being an physically alive person here \n \n now child, we don't receive many spiritual travellers here, \n I assumed your in seek of help \n please feel free to ask away",hashmaprooms.get("denver"));
    	steve.setThank("Oh thanks buddy");
    	//These NPCs have set location and are static
    	lord = new Chipotlian("Lord","I shalt see thee at the monastery, my child",hashmaprooms.get("chipotle"));
    	lord.setThank("Thank you for your generosity my child");
    	morganfreeman = new Chipotlian("Freeman","",hashmaprooms.get("extraguac"));
    	morganfreeman.setThank("Thank you, son");
    	priest = new Chipotlian("DatPriest","",hashmaprooms.get("salsa"));
    	priest.setThank("Thank you my savior");
    	angel = new Chipotlian("Angel","Wubba lubba dub dub!",hashmaprooms.get("entrance"));
    	angel.setThank("Yo u srsly? Thanks a million homie, u slay");
    	//same hashmap 
    	chipotlians.put("Steve", steve);
        chipotlians.put("Lord", lord);
        chipotlians.put("Freeman", morganfreeman);
        chipotlians.put("DatPriest", priest);
        chipotlians.put("Angel", angel);

    	
    }
    private void createItems() {
    	//create items
    	Item avo,magicavo,bowl,burritobread,tacotent,avomen,docpep,map;
    	
    	magicavo = new Item("MagicAvocado","*\nLeave mark on a place, it can give u the magic to moonwalk, \njust like Michael!",5);
    	avo = new Item("Avocado","\n*A super good looking fresh avocado. \nBeautiful and Attractive you just can't wait to marry her",1);
    	bowl = new Item("Bowl","\n*What you need to cross over the guac sea",8);
    	burritobread = new Item("BurritoBread","\n*A plain and dry burrito bread, so plain and dry like ur skin on earth ",8);
    	tacotent = new Item("TacoTent","\n*U need dis to travel in any salsa area",5);
    	avomen = new Item("Avomen","\n*A sacre holy-blend sacrosanct guac. \n*Paste it on your forehead as a sign of holiness. \n*A pass for entering the Land of Chipotle.",0.5);
    	docpep = new Item("DoctorPepper","\n*Marvellous sugary soda drink. \n*What you wanna hear is you can't get fat here in your afterlife \n*Exchangeable for certain service from certain chipotlians",2);
    	map = new Item("MapX","\n*An edible map pill that looks and tastes just like chocolate  \n*Gives you visions of the geographic details of the entire Chipotle \n*Effects last for only one wiggling",1);
    	//ArrayLists categorized for future uses
    	nonNPCitems = new ArrayList<>(Arrays.asList(avo,magicavo,burritobread,docpep));
    	NPCitems = new ArrayList<>(Arrays.asList(bowl,tacotent,avomen,map));
    	allitems = new ArrayList<>(Arrays.asList(avo,magicavo,burritobread,docpep,bowl,tacotent,avomen,map));
    	// hashmap for future access
		hashmapitems.put("MagicAvocado", magicavo);
		hashmapitems.put("Avocado", avo);
		hashmapitems.put("DoctorPepper", docpep);
		hashmapitems.put("burritobread", burritobread);

    	
    }
    private void createDocs() {
    	Random location = new Random();
    	//create quest related documents
    	Docs doc1,doc2,doc3,doc4,doc5;
    	
    	doc1 = new Docs("FourthOfJuly","It was 2018, on a fourth of July",rooms.get(0));
    	doc2 = new Docs("doc2","It was 2018, on a fourth of July",rooms.get(location.nextInt(20)));
    	doc3 = new Docs("doc3","It was 2018, on a fourth of July",rooms.get(location.nextInt(20)));
    	doc4 = new Docs("doc4","It was 2018, on a fourth of July",rooms.get(location.nextInt(20)));
    	doc5 = new Docs("doc5","It was 2018, on a fourth of July",rooms.get(location.nextInt(20)));
    	//An array list for all document collection verifying
    	alldocs = new ArrayList<>(Arrays.asList(doc1,doc2,doc3,doc4,doc5));
    	
    }
    /**
     * 
     * @param command for searching items in current location
     */
    private void sniff(Command command) {
    	ArrayList<Item> x = nonNPCitems;
    	Random rand = new Random();
    	boolean docsHere = false;
    	boolean emptyroom = false;
    	Room randroom = rooms.get(rand.nextInt(20));
    	if (currentRoom.equals(randroom)) {
    		emptyroom = true; //current location is randomly selected as an empty room (just adding normality to the game so that not everyroom has items when first time there)
    	}
    	for (Docs y: alldocs) {
    		if (y.ifincurrentLoc(currentRoom)) {
    			
    			docsHere = true; //docs are present in this location
    			break;
    		}
    	
    	}
    	
    	if ((emptyroom==true)&&(docsHere==false)) {
    		System.out.println("Hmm hella good sniff but there's nothing here");
            //when current room is randomly selected as empty room or there's literally nothing assigned to this room, inform player
    	}
    	else {
    		System.out.println("You sniff out the following items:");
    		System.out.println();
    		for(Item i: itemsInCurrentLoc) {
    			found.add(i); 
    			System.out.println(i.getName()+" :  "+i.getDescription());
    			System.out.println("   Item Weigh: " +i.getWeight()+" lbs");
    			System.out.println();
    			System.out.println();
    		
    		
    		
        	}
    		for (Docs doc: alldocs) {
        		if (doc.ifincurrentLoc(currentRoom)) {
        			System.out.println("You've found a document related to your death:");
        			System.out.println("Book:  "+doc.getName());
        			misc.add(doc);
        			
        		}
        	
        	}
    		
    		System.out.println();
    		System.out.println("Good boy!Now you can choose the item to grab them");
    	}
       
    }
    /**
     * 
     * @param command for dropping certain item
     */
    private void backflip(Command command) {
    	ArrayList<String>names = new ArrayList<>();
    	for (Item a:chipsack) {
    		names.add(a.getName());
    	}
    	if(chipsack.isEmpty()) {
    		System.out.println("uhh there's nothing to get rid of in yer chipsack");
    	}
    	else if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("get rid of what?");
            return;
        }
    	
    	
    	else if (!names.contains(command.getSecondWord())) {
    		System.out.println("There's nothing like that to drop");
    		return;
    	}
    	
    	else
    	{
    		for (Item a:chipsack) {
    			
    			if(a.getName().equals(command.getSecondWord())){
    				System.out.println("You've just droped:"+a.getName());
    				chipsack.remove(a);
    			}
    		}
    	}
    }
    /**
     * 
     * @param command for giving certain item to certain NPC, it will trigger certain shortcut items for better gaming experience
     */
    private void give(Command command) {
    	ArrayList<String>names = new ArrayList<>();
    	for (Chipotlian value:chipotlians.values()) {
        	if (value.ifincurrentLoc(currentRoom)){
        		names.add(getChipotliansKey(value));
        	}
    	}
    	ArrayList<String>giveitems = new ArrayList<>();
    	for (Item a:chipsack) {
    		giveitems.add(a.getName());
    	}
    	if (present ==false) {
    		System.out.println("There's no one here to give items to");
    		
    	}
    	else {
    		if ((!command.hasSecondWord())) {
    			System.out.println("give what?");
    		
    		}
    		else {
    			if ((!command.hasThirdWord())) {
    	    		System.out.println("give that to whom?");
    	    	}
    			else {
    				if (!giveitems.contains(command.getSecondWord())) {
        	    		System.out.println("Uhh there's nothing like that in your chipsack");
        	    	
        	    	}
    				else {
    					if (!names.contains(command.getThirdWord())) {
            	    		System.out.println("Uhh no one here like that to give this item to");
            	    		
            	    	}
    					else if (names.contains(command.getThirdWord())){
            	    		String NPC = command.getThirdWord();
            	    		String givenitem = command.getSecondWord();
            	    		switch (NPC) {
            	    		case "Bowie":
            	    			if (givenitem == "DoctorPepper") {
            	    				bowiehasPep = true;
            	    				break;
            	    				
            	    			}
            	    			else {

            	    				break;
            	    			}
            	    		
            	    				
            	    		case "Hawking":
            	    			if (givenitem =="DoctorPepper") {
            	    				hawkinghasPep = true;
            	    			}
            	    			else {

            	    				break;
            	    			}
            	    		case "Wonka":
            	    			if (givenitem =="DoctorPepper") {
            	    				hawkinghasPep = true;
            	    			}
            	    			else {

            	    				break;
            	    			}
            	    			
            	    		}
            				chipsack.remove(hashmapitems.get(givenitem));

            	    		System.out.println(chipotlians.get(NPC).getThank());
            	    		System.out.println("\nYou have given " + command.getSecondWord()+" to "+command.getThirdWord());
            	    		System.out.println("\n\n"+currentRoom.getLongDescription());
            	    		}
    				}
        	    		
    			}
    	    		
    		}
    		
    		}
    }
    /**
     * 
     * @param command for using certain item in your CHIPSACK
     */
    private void use(Command command) {
		// generate an array list for strings comparison 
    	ArrayList<String>names = new ArrayList<>();
    	for (Item a:chipsack) {
    		names.add(a.getName());
    	}
    	
    	if(chipsack.isEmpty()) {
    		System.out.println("uhh there's nothing to use here"); // if there's nothing in your bag, you just can't use anything then
    	}
    	else if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("use what?");
            return;
        }
    	
    	
    	else if (!names.contains(command.getSecondWord())) {
    		System.out.println("There's nothing like that to use");
    		return;
    	}
    	
    	else
    	{
    		for (Item a:chipsack) {
    			
    			if(a.getName().equals(command.getSecondWord())){
    				System.out.println("You've just used:"+a.getName());
    	    		switch(a.getName()) {
    	    		case "MagicAvocado":
    	    			setmark();
    	    			
    	    			break;
    	    		case "Avocado":

    	    			if (nutrition<80) {
    	    				nutrition +=20; //increase nutrition for more wiggling movements everytime you eat an avocado
    	    				eatenavo.add(a);
        	    			System.out.println("You ate an avocado\nhalf an hour later, nothing seems to happen\nbut you do feel less hungry ");

    	    			}
    	    			else {
    	    				nutrition = 80; //when your nutirion is already max, you can't eat more
    	    				System.out.println("Your full, you can't eat anymore food,wiggle somewhere, \nwaste some energy and come back try to eat it again");
    	    				return;
    	    			}
    	    			break;
    	    		case "BurritoBread":
    	    			System.out.println("You ate a piece of burrito bread, it was so plain and so dry. \n\nYou regretted it so much \neven tho it makes you feel less hungry, you can't stop blaming yourself \n\"dude why in heaven would you eat that bread?\" ");
    	    			if (nutrition<80) {
    	    				nutrition +=10;//increase nutrition for more wiggling movements everytime you eat a piece of burrito bread
    	    			}
    	    			else {
    	    				nutrition = 80;
    	    				System.out.println("Your full, you can't eat anymore food");
    	    				return;
    	    			}
    	    			break;
    	    		case "TacoTent":
    	    			System.out.println("You applied the portable taco tent! \nNow you can travel freely in any salsa area");
    	    			salsapass= true; //give you access to all salsa areas in the game
    	    			currentObjective+=1; //trigger next objective to find the missing priest
    	    			break;
    	    		case "Bowl":
    	    			System.out.println("You applied the bowl on your waist \n(dw, its supposed to be worn like a skirt). \nNow you can travel across the sea");
    	    			guacseapass=true; //give you access to guac sea and the western part of the game map
    	    			currentObjective+=1; //trigger next objective: to find next quest NPC who is on the other side of the guac sea
    	    			break;
    	    		case "DoctorPepper":
    	    			System.out.println("OW YAS, WHAT A SIP OF HEAVEN!!!!\n(even tho u are in heaven, slangs from earth are very common here)");
    	    			
    	    			break;
    	    		case "Avomen":
    	    			System.out.println("You pasted some Holy Guac on your forehead. \nYou look very dumb, \nbut to chipotlians, it's a very charming looking ");
    	    			chipotlepass = true; //give you access to the chipotle palace
    	    			currentObjective+=1;  //trigger objective to meet Lord Chipotle
    	    			break;
    	    		case "MapX":
    	    			System.out.println("You chewed the MapX. \nYour current vision turns into a giant map of the entire Chipotle geography");
    	    			//more code here to insert game map image
    	    			mapUsed = true;
    	    			
    	    		}
    	    		chipsack.remove(a);
    	    		break;
    			}
    		
    				
    		}
    		
    	
    	}
    	
	}
    /**
     * 
     * @param command for picking up obejcts found in current location
     */
    private void pick(Command command) {
    	double currentweight = calculateChipsackWeight(chipsack);
    	
    	ArrayList<String>names = new ArrayList<>();
    	ArrayList<String>notes = new ArrayList<>();

    	for (Item a:found) {
    		names.add(a.getName());
    	}
    	for (Docs n:misc) {
    		notes.add(n.getName());
    	}
    	if(found.isEmpty()&&misc.isEmpty()) {
    		System.out.println("Stop hallucinating dude.There's nothing to grab here"); //if there's no items nor docs at current place
    		return;
    	}
    	else if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("grab what?");
            return;
        }
    	
    	
    	else if (!names.contains(command.getSecondWord())&&!notes.contains(command.getSecondWord())) {
    		System.out.println("There's nothing like that to grab");
    		return;
    	}
    	
    	else if (currentweight>chipsacklimit) {
    		System.out.println("Your overloaded, even tho this is heaven, everything has a limit!");
    		System.out.println("You need to get rid of some of the messy stuffs in ur chipsack to take item or notes. ");
    		System.out.println("Try the backflip command");
    		return;
    	}
    	else
    	{
    		for (Item a:found) {
    			
    			if(a.getName().equals(command.getSecondWord())){
    				System.out.println("You've just grabbed:"+a.getName());
    	    		chipsack.add(a); //add to item to your bag
    	    		found.remove(a); // remove its presence at current location
    	    		itemsInCurrentLoc.remove(a);
    	    		break;
    			}
    		
    		for (Docs x:misc)	{
    			if(x.getName().equals(command.getSecondWord())){
    				if (notebook.contains(x)) {
    					System.out.println("You've already added this book to your notebook");
    					break;
    				}
    				else {
    					System.out.println("You've just added: "+x.getName()+" to your notebook");
    					notebook.add(x);
    					misc.remove(x); //same functioning as items
    					break;
    				}
    			
    			}
    		
    			}
    		}
    	}
    	
    	
    }
    //a method for calculating total weight of all items in bag, used in checking if exceeding limits
    private double calculateChipsackWeight(ArrayList<Item> chipsack) {
		double total = 0;
    	for (Item x : chipsack) {
    		total = total +x.getWeight();
    	}
    	
    	return total;
    	
    }
   /**
    * 
    * @param command for talking to NPCs
    */
    private void yo(Command command) {
    	ArrayList<String>names = new ArrayList<>();
    	for (Chipotlian value:chipotlians.values()) {
        	if (value.ifincurrentLoc(currentRoom)){
        		names.add(getChipotliansKey(value));
        	}
    	}
    	if (present ==false) {
    		System.out.println("There's no one here to yo to");
    		return;
    	}
    	else if (present == true && !command.hasSecondWord()) {
    		System.out.println("yo to who genius");
    	}
    	else if (present == true && !names.contains(command.getSecondWord())) {
    		System.out.println("Uhh there's no one named that to yo to here");
    	}
    	else if (present == true&& names.contains(command.getSecondWord())){
    		String NPC = command.getSecondWord();
    		
    		switch(NPC) {
    			case "Freeman":
    				if (currentObjective<3) {
        				System.out.println("Morgan Freeman: ");
        			    System.out.println("You don't need me now son. You must have other stuffs to focus on ");
        			    
        				break;
    				}
    				else {
    					chipsack.add(NPCitems.get(1));
        				System.out.println("Morgan Freeman: ");

    		    		System.out.println(chipotlians.get(NPC).getConvo());
    		    		System.out.println("\n\n You have received a Taco Tent from Morgan Freeman!");
    					break;
    				}
    				
    			case "Steve":
    				//quest message will not be triggered until the quest become current quest
    				// if not current quest, current objective will be reminded
    				if(currentObjective<2) {
    					System.out.println("Steve Ells: ");
    					System.out.println("Welcome to Denver, Colorado, stranger!");
    					System.out.println("(You don't even know why you are here for)");
    					System.out.println("(Focus on your progress in your notebook! Jeez, can't believe I took all those notes for ya for nothing!)");
    					break;
    				}
    				else {
    					chipsack.add(NPCitems.get(0));
    					System.out.println("Steve Ells: ");
    		    		System.out.println(chipotlians.get(NPC).getConvo());
    		    		System.out.println("\n\n You have received a Bowl from Steve");
    					break;
    				}
    			case "Stan":
		    		System.out.println(chipotlians.get(NPC).getConvo());
		    		break;

    			case "DatPriest":
    				//when not in prist quest, quest message will not be triggered
    				if (currentObjective<4) {
    					System.out.println("DatPriest: ");
    					System.out.println("Umm?");
    					System.out.println("(geez how the heck did u get here? u must be a glitch one that died in a really peculiar way on earth)");
    					break;
    				}
    				//during priest quest, quest conversation is triggered
    				else {
    					chipsack.add(NPCitems.get(2));//get Avomen Paste for entering Chipotle Palace when used
    		    		System.out.println(chipotlians.get(NPC).getConvo());
    		    		System.out.println("\n\n You have received the Avomen paste from DatPriest");

    					break;
    				}
    				
    			case "Angel":
    				System.out.println("Dan: ");
    				System.out.println("Hey I'm an angel, I'm here to help");
		    		System.out.println(chipotlians.get(NPC).getConvo());

    				printWhat();
    				break;
    			case "Wonka":
			        System.out.println("Gene Wilder (Willy Wonka): ");
		    		System.out.println(chipotlians.get(NPC).getConvo());
		    		System.out.println("To help you, i'd need some doctor pepper");
    				if (wilderhasPep) {
    					System.out.println("\n(crafting MapX )\n\n");
    					chipsack.add(NPCitems.get(3));
    					System.out.println("You have received MapX from Willy Wonka");
    					wilderhasPep = false;
    					break;
    					
    				}
    				//when there's no Doctor Pepper, you miss the chance of getting the shortcut item unless you found some Doctor Peppers and got to meet the NPC again
    				else {
    					System.out.println("\n (your back in real life)\n");
    					System.out.println("(Find some doctor peppers)");
    					break;
    				}
    			case "Hawking":
		    		System.out.println(chipotlians.get(NPC).getConvo());

    				if (hawkinghasPep) {
    					System.out.println("I don't have much details, but here are the locations of some of the people you need to meet\n\n[spoiler alert]\n\n");
    					if(currentObjective<3){
        					System.out.println("\n\nIf you need to find Steve, he's in the spiritual Denver, south of Burrito Coast,\n Burrito Coast is on the west side of Tortilla Village, \nwhich is the north to the gate entrance");

    					}
    					else if(currentObjective==3) {
        					System.out.println("\n\nIf you need to find Morgan Freeman, he's in the ExtraGuac Province, \nonce you crossed over the Guac Sea and arrived at Guac Hub II, \ngo west and you'll see him there");

    					}
    					else if (currentObjective >3) {
    						System.out.println("\n\n If you need to find DatPriest, \nhe's lost somewhere in the middle of Salsa Desert on the east continent, \n which is right next to the Cow Dump Farm" );
    					}
    					chipotlians.get(NPC).getThank();
    					hawkinghasPep = false;
    					break;
    				}
        			//when there's no Doctor Pepper, you miss the chance of getting the shortcut item unless you found some Doctor Peppers and got to meet the NPC again

    				else {
    					System.out.println("\n (Find some doctor peppers)\n");
    					break;
    				}
    			case "Bowie":
		    		System.out.println(chipotlians.get(NPC).getConvo());

    				if(bowiehasPep){
    					System.out.println("Your weight limit has increased by 5");
    					chipsacklimit +=5.0;
    					System.out.println("Oh and go find Wonka, he might offer you some help too");
    					System.out.println("Best of luck!");
    					chipotlians.get(NPC).getThank();
    					bowiehasPep = false;
    					break;
    				}
    				else {
    					System.out.println("\n (Find some doctor peppers)\n");
    					break;
    			}
    			case "Dude":
		    		System.out.println(chipotlians.get(NPC).getConvo());
					chipsack.add(nonNPCitems.get(1));
					chipsack.add(nonNPCitems.get(1));
					
					System.out.println("\n You have received two burrito bread from a random dude \n");
					break;
		    		
    			case "Lord":
    				if (currentObjective<5) {
    					System.out.println("Lord Chipotle: ");
    					System.out.println("Intruder!");
    					System.out.println("(run!)");
    					
    					break;
    				}
    				else {
    					if(chipotlemet==false) {
    						System.out.println("Lord Chipotle: ");
    				        System.out.println("Greeting, my child. What hath brought thou here? ");
    				        System.out.println("(tell the story) ");
    				        System.out.println("Oh dang, that's a mess up story");
    				        System.out.println();
    				        System.out.println("Tho,to help you become a true chipotlian, i'm afraid I'll still need your files");
    				        System.out.println("do you happen to have them?");
    				        System.out.println();
    				        System.out.println("(hand in)");
    				        System.out.println();	        
							if(check()==false) {
	    				        
	    				        System.out.println("Lord Chipotle:");
	    				        System.out.println("Hmm, this is not enough for me to proceed your case");
	    				        break;
							}
	    				    else {
	    				    	
	    				    	System.out.println("Hooha!that is enough information for me to avocaptize you");
	    				    	System.out.println("and make you a permanent citizen of your afterlife!");
	    				    	System.out.println();
	    				    	System.out.println("The avocaptizing ceremony will be carried at the Monastery");
	    				    	System.out.println("(hoo,sick)");
	    				    	
	    				    	chipotlemet = true;
	    				    	break;
	    				    }
    					}
    					else {
        		    		System.out.println(chipotlians.get(NPC).getConvo());
        		    		break;

    					}
    				}
    		}
    		System.out.println();
    		System.out.println(currentRoom.getLongDescription());

    				
    			
    				
    		}
    		
    	}
    	
    

    // methods for geting hashmap value with the key
    private String getHashMapRoomsKey(Room r)
    {
    	for(String key : hashmaprooms.keySet())
    	{
    		if(hashmaprooms.get(key)==r)
    		{
    			return key;
    		}
    	}
    	return "";
    }
    private String getChipotliansKey(Chipotlian c)
    {
    	for(String key : chipotlians.keySet())
    	{
    		if(chipotlians.get(key)==c)
    		{
    			return key;
    		}
    	}
    	return "";
    }
    
    
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean status = check();  
        
        while (! finished) {
            Command command = parser.getCommand();
            processCommand(command);
        }
        //win the game when you have all documents, meet the finished criteria in game, and in good health
        if (status&&(nutrition>0)) {
        	System.out.println("You woke up in your computer chair");
    		System.out.println();

        	System.out.println("Haha");
    		System.out.println();
    		System.out.println("Must be the all-night-coding syndrome again");
    		System.out.println();
    		System.out.println("Maybe you should just have listened to your GP: stop doing codes");
    		System.out.println("Although, congrats, Dan.");
    		System.out.println("At least, in this reality you survived and finished your PPA assignment");
    		finished = false;

    		
        }
        //lose the game if you are in starvation
        else if(nutrition < 0){
        	System.out.println("Dude, your too week to move. Angels emergency has arrived to help you");
        	System.out.println("By the time they arrived, you have stuck in the permanet unholy state of starvation");
        	System.out.println("luckily, as a green card holder, you are still in settling weeks,");
        	System.out.println("since time is a movable dimension here, feel free to rewind your time by simply restarting");
        	finished =false;
        			
        }
        //quit the game
        else {
        	System.out.println("This is ur afterlife, technically u can't quit");
        	System.out.println("or die again whatsoever" );
        	System.out.println("So just to let you know homie, ur in a permanent state of alcholic intoxication");
        	System.out.println("just restart, if you ever need some bloody mary to wake yourself up");
        	finished = false;
        }
        
    }
        

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Chipotle, and you are dead! Hooray!");
        System.out.println("My name is Chatty. I'm the gate guard.");
        System.out.println("You are in the afterlife, the Holy Kingdom of Lord Chipotle");
        System.out.println("This is the place where most of you humans' religions described as heaven");
        System.out.println("Unfortunately,I'm afraid I have some bad news:");
        System.out.println();

        System.out.println("Apparently, "
        		+ "you are still in the process of immigrating here. ");
        System.out.println("Here's a bit of a long story: ");
        System.out.println("Once upon a time, there's an angel, \nwho used to be a Compsci major student in college\n");
        System.out.println("He died from his workaholism for an assignment "
        		+ "from a mystical college class called PPA");
        System.out.println("\n (rumors said it's a torturing society from earth) \n");
        System.out.println("Anyway,that's not important. ");
        System.out.println();

        System.out.println("The issue is, when he died and came to Chipotle as a file maitenance clerk, ");
        System.out.println("he was still carrying that typical-compsci-student-slacking-off altitude with him ");
        System.out.println("On the day you died, he was night-out partying with his bros ");
        System.out.println(" (pss,guess what,rumors claimed they all died from the same cause). \n (what a coincidence, lol)");
        System.out.println();

        System.out.println("So the death record printer kept printing your files while no clerk was on duty on the day u died");
        System.out.println("And the next day, the records were all gone \n(it was assumed that some wind must have blown them away)" );
        System.out.println("Therefore, we weren't informed about your cause of death at all "
        		+ "\nand the Lord doesn't seem to be aware of this either");
        System.out.println();

        System.out.println("Now, I supposed your stuck here as a temporary green card holder");
        System.out.println("Hope this situation can't be solved somehow. I do wish you best luck");
        System.out.println();

        System.out.println("In the meantime,say 'what' or talk to your personal angel if thou art still confused");
        
        System.out.println();
        System.out.println("If not, try the following commands:");
        parser.showCommands();
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        for (Chipotlian chipo:chipotlians.values()) {
        	if (chipo.ifincurrentLoc(currentRoom)){
        		System.out.println();
        		System.out.println(chipo.getName()+" is in your area");
        		System.out.println("Try yo to him");
        		present = true;
        //NPCs randomly generated at the beginning of the game
        	}
        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private void processCommand(Command command) 
    {

        if(command.isUnknown()) {
            System.out.println("pls,speak english, thou shalt");
            return;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("what")) {
            printWhat();
        }
        else if (commandWord.equals("wiggle")) {
            goRoom(command);
        }
        else if (commandWord.equals("CHIPSACK")) {
        	System.out.println("Here's ur chipsack");
        	System.out.println("-----------------------------------------------------------------------------------------");

        	for (Item x :chipsack) {
        		System.out.println(x.getName()+" :  "+x.getDescription());
        		System.out.println("Item weighs:  "+x.getWeight()+" lbs");
        		System.out.println();
        	}
        	System.out.println();
        	System.out.println("Total: "+calculateChipsackWeight(chipsack)+"  lbs");
        	System.out.println("-----------------------------------------------------------------------------------------");
        	System.out.println("                         Nutrition: "+nutrition);
        	System.out.println("do a backflip if you wanna get rid of certain item, ");
        	System.out.println("try use command if you wanna use certain item(s)");
        	//format for chipsack when call
        }
        else if (commandWord.equals("NOTEBOOK")) {
        	System.out.println("Here's ur notebook");
        	System.out.println("-----------------------------------------------------------------------------------------");
        	for (Docs x :notebook) {
        		System.out.println(x.getName()+" :  ");
        		System.out.println("\""+x.getDescription()+"\"");
        		System.out.println();
        	}
        	System.out.println("-----------------------------------------------------------------------------------------");
        	System.out.println("Current objective: ");
        	System.out.println(questList.get(currentObjective).getInfo());
        
        	System.out.println("-----------------------------------------------------------------------------------------");
        	//format for notebook when call
        }
        else if (commandWord.equals("yo")) {
        	yo(command);
        }
        
        else if (commandWord.equals("moonwalk")) {
            fasttravel();
        }
        else if (commandWord.equals("backflip")) {
            backflip(command);
        }
        else if (commandWord.equals("quit")) {
            finished = quit(command);
        }
        else if (commandWord.equals("grab")) {
        	pick(command);
        }
        else if(commandWord.equals("sniff")) {
        	sniff(command);
        }
        else if (commandWord.equals("use")) {
        	use(command);
        }
        else if (commandWord.equals("give")) {
        	give(command) ;
        }
        	
        
        // else command not recognised.
        
    }

    

	// implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printWhat() 
    {
        System.out.println("You are lost. You are alone.");
        System.out.println();
        System.out.println("but ya still donno what ya doin");
        System.out.println("in your seemingly mundane afterlife");
        System.out.println("which was supposed to be dope and fun");
        System.out.println("bleh, beat!");
        System.out.println();
        System.out.println("alas..i totally feel ya homie");
        System.out.println("How could any angel in frickin heaven mess up one's earth exit record?");
        System.out.println("And it must be sucky that that only one earthling has encountered this happened to be you");

        System.out.println();
        System.out.println("Welp,don't lose ur sauce homie,there's only one thing we can do to correct this:");
        System.out.println("Find out how you died.");
        System.out.println();
        System.out.println("My name is Jian. Most people called me Dan (typical Asian American issue).");
        System.out.println("To help you better get started,");
        System.out.println("The below options are suggested:");
        parser.showCommands();
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("Wiggle:");
        System.out.println("In the afterlife, ");
        System.out.println("You can only explore by wiggling somewhere. It might sound weird but get used to it");
        System.out.println("Angels here are very fond of those who wiggle in an attractive way");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("Sniff: ");

        System.out.println("The sense of your nose is maximized here.You can sniff to search items around you");
        System.out.println("All items info will be listed in your chipsack(your storage place)");
        System.out.println("Sometimes, certain items may appear to be related to your death case, "
        		+ "and pls, do remember to collect them!");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("Grab");
        System.out.println("To grab the item you found, simply grab it (why am i explaining this)");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("Use:");
        System.out.println("To use something in your chipsack, you can umm, \"use\" it");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("Backflip");
        System.out.println("You can do a backflip to get rid of something you don't want in your bag! \nHoly moly, how dope is that!");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        System.out.println("Moonwalk:");
        System.out.println("By doing a moonwalk, you can fast travel back to certain places. ");
        System.out.println("However, you'll need a Magic Avocado to perform such action");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("CHIPSACK and NOTEBOOK:");
        System.out.println("By calling CHIPSACK or NOTEBOOK, you can track your current acquired items ");
        System.out.println("or your evidence and current objective(dw homie, I'm taking all the notes for ya)");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        System.out.println("Yo:");
        System.out.println("Oh and you can also talk to some of the cool kids around here by saying yo to them");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");

       

        System.out.println("Now, to win the game, you'll have to ");
        System.out.println();
        System.out.println("collect all the files that indicate how you died");

        System.out.println();
        System.out.println("and");
        System.out.println();
        System.out.println("Wiggle to the palace of Holy Lord Chipotle with your evidence and meet thy Lord in person");
        System.out.println();
        System.out.println("Whenever ur confused, feel free to say'what'and I'll pop out from nowhere to help ya");
        System.out.println("And whenever you feel like quitting the game, just say 'quit' in a really posh accent");
        System.out.println();
        System.out.println("Enjoy ur afterlife!");
        //a long tutorial trigger when talking to angel or command what is called
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {	//warning player about the low nutrition level
    	if(nutrition<=20) {
    		System.out.println("Your starving, find and grab something to eat, ");
    		System.out.println("You remaining strength can only help you wiggle to "+nutrition/10+" places");
    	}
    	//when nutrition is zero, player can't move, game ends
    	if (nutrition<0) {
        	finished = true;
        }
    	//verification for completion of quest 0
    	Random randloc = new Random();
    	if ((avoinbelly==false)&&eatenavo.contains(nonNPCitems.get(0))) {
    		currentObjective+=1;
    		avoinbelly = true;
    		
    	}
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("wiggle where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("hmm that's the angels' bathroom.");
            System.out.println("we can't wiggle there, can we wiggle somewhere else?");
        }
        //if the next location is beanmine, go to a random room in the accessible part of current game map
        else if (HMequalsnextroom("beanmine",nextRoom)) {
        	System.out.println(hashmaprooms.get("beanmine").getShortDescription());
        	if (currentObjective<3) {
        		nextRoom = area1.get(randloc.nextInt(20)); 
        	}
        	else if (currentObjective ==3) {
        		nextRoom = area2.get(randloc.nextInt(20)); 

        	}
        	else if (currentObjective>3) {
        		nextRoom = area3.get(randloc.nextInt(20)); 

        	}
			
			for (Chipotlian v:chipotlians.values()) {
				if (questcharacternames.contains(v.getName())) {
					
				}
				else {
					v.move(rooms.get(randloc.nextInt(20)));
				}
            	
            }
			currentRoom = nextRoom;
			nutrition-=10; //every move consume 10 nutrition
			System.out.println("You woke up completely a mess at some random place.");
			System.out.println(currentRoom.getLongDescription());
			String name = getHashMapRoomsKey(currentRoom);
            itemsInCurrentLoc = hashmapstorageitems.get(name);
            present = false;
            for (Chipotlian chipo:chipotlians.values()) {
            	if (chipo.ifincurrentLoc(currentRoom)){
            		System.out.println();
            		System.out.println(chipo.getName()+" is in your area");
            		System.out.println("Try say \"yo\" to him");
            		present = true;
            	}
            }
        }
        else if (HMequalsnextroom("guacsea",nextRoom)){
        	if (guacseapass==false) { //when you don't have access to guac sea, guac sea is limited. current objective is reminded
        		if(currentObjective<1) {
        			System.out.println("There's the sea in front of you, but you can't swim! ");
        			System.out.println("But there's more important things to do, find some avocados");
        	        System.out.println("or u will suffer from the lack of holiness");
        		}
        		else {
        			System.out.println("There's the sea in front of you. ");
        	        System.out.println("Welp, you can't swim, you need a bowl to cross over the guacsea, ");
            		System.out.println("go find Steve, he knows how to make a bowl");
            		System.out.println(currentRoom.getLongDescription());
            		currentObjective+=1;
        		}
        	}
        	else {
        		for (Chipotlian v:chipotlians.values()) {
    				if (questcharacternames.contains(v.getName())) {
    					
    				}
    				else {
    					v.move(rooms.get(randloc.nextInt(20)));
    				}
                	
                }
                currentRoom = nextRoom;
                nutrition-=10;
                System.out.println(currentRoom.getLongDescription());
                String name = getHashMapRoomsKey(currentRoom);
                itemsInCurrentLoc = hashmapstorageitems.get(name);
                present = false;
                for (Chipotlian value:chipotlians.values()) {
                	if (value.ifincurrentLoc(currentRoom)){
                		System.out.println();
                		System.out.println(value.getName()+" is in your area");
                		System.out.println("Try yo him");
                		present = true;
                	}
                }
        		
        	}
        }
        else if (HMequalsnextroom("church",nextRoom)){ //end game scene triggered when end objectives are complete
        	if ((chipotlemet==true)&&(check())) {
        		System.out.println("You are at the monastery, by the guac river");
        		System.out.println("Everyone is here with you,");
        		System.out.println("Some you haven't met yet: ");

        		System.out.println("Even Steve and Morgan Freeman, who you spiritually met, travelled from earth to be here for you");
        		System.out.println("Every chipotlian is at present, including those college angels who have died from the PPA battle");

        		System.out.println("Lord Chipotle is right in front of you");
        		System.out.println("DatPriest you saved started praying:");
        		
        		System.out.println();

        		System.out.println("You see Dan is waving bye at you");
        		System.out.println("Everyone is waving bye at you");
        		
        		System.out.println();
        		System.out.println("hum, weird");
        		System.out.println();


        		System.out.println("You see lights");
        		System.out.println();

        		System.out.println("Bright.");
        		System.out.println();
        		System.out.println(".....");
        		System.out.println();
        		System.out.println("You felt the holiness inside your body bursting out");
        		System.out.println();
        		System.out.println("That's the feeeeeling");

        		finished = true;
        	}
        	else {
        		for (Chipotlian v:chipotlians.values()) {
    				if (questcharacternames.contains(v.getName())) {
    					
    				}
    				else {
    					v.move(rooms.get(randloc.nextInt(20)));
    				}
                	
                }
                currentRoom = nextRoom;
                nutrition-=10;
                System.out.println(currentRoom.getLongDescription());
                String name = getHashMapRoomsKey(currentRoom);
                itemsInCurrentLoc = hashmapstorageitems.get(name);
                present = false;
                for (Chipotlian value:chipotlians.values()) {
                	if (value.ifincurrentLoc(currentRoom)){
                		System.out.println();
                		System.out.println(value.getName()+" is in your area");
                		System.out.println("Try yo him");
                		present = true;
                	}
                }
        		
        	}
        }
        else if ((HMequalsnextroom("salsa",nextRoom))||(HMequalsnextroom("mild",nextRoom))||(HMequalsnextroom("medium",nextRoom))||(HMequalsnextroom("hot",nextRoom))){
        	if (salsapass == false) { //same limitation effects as guac sea
        		if (currentObjective<3) {
        			System.out.println("Dude, your not supposed to be around here.");
        			System.out.println( "You can seriously get a sunburn. Focus on your current objective!");
        		}
        		else {
	        		System.out.println("Your skin is too vulnerable to endure the heat in any salsa area ");
	        		System.out.println("go find Morgan Freeman, he knows what to do");
	        		System.out.println(currentRoom.getLongDescription());
        		}
        	}
        	else {
        		for (Chipotlian v:chipotlians.values()) {
    				if (questcharacternames.contains(v.getName())) {
    					
    				}
    				else {
    					v.move(rooms.get(randloc.nextInt(20)));
    				}
                	
                }
                currentRoom = nextRoom;
                nutrition-=10;
                System.out.println(currentRoom.getLongDescription());
                String name = getHashMapRoomsKey(currentRoom);
                itemsInCurrentLoc = hashmapstorageitems.get(name);
                present = false;
                for (Chipotlian value:chipotlians.values()) {
                	if (value.ifincurrentLoc(currentRoom)){
                		System.out.println();
                		System.out.println(value.getName()+" is in your area");
                		System.out.println("Try yo him");
                		present = true;
                	}
                }
        		
        	}
        }
        else if (HMequalsnextroom("chipotle",nextRoom)){
        	if (chipotlepass == false) { // same limitation effect
        		System.out.println("To enter the Chipotle, you need the Holy Guac symbol ");
        		System.out.println("go find and help the missing priest, i'm sure he'd figure something out");
        		System.out.println(currentRoom.getLongDescription());
        	}
        	else {
        		for (Chipotlian v:chipotlians.values()) {
    				if (questcharacternames.contains(v.getName())) {
    					
    				}
    				else {
    					v.move(rooms.get(randloc.nextInt(20)));
    				}
                	
                }
                currentRoom = nextRoom;
                nutrition-=10;
                System.out.println(currentRoom.getLongDescription());
                String name = getHashMapRoomsKey(currentRoom);
                itemsInCurrentLoc = hashmapstorageitems.get(name);
                present = false;
                for (Chipotlian value:chipotlians.values()) {
                	if (value.ifincurrentLoc(currentRoom)){
                		System.out.println();
                		System.out.println(value.getName()+" is in your area");
                		System.out.println("Try yo him");
                		present = true;
                	}
                }
        		
        	}
        }
        
        else {
        	for (Chipotlian v:chipotlians.values()) {
				if (questcharacternames.contains(v.getName())) {
					
				}
				else {
					v.move(rooms.get(randloc.nextInt(20)));
				}
            	
            }
            currentRoom = nextRoom;
            nutrition -=10;
            System.out.println(currentRoom.getLongDescription());
            String name = getHashMapRoomsKey(currentRoom);
            itemsInCurrentLoc = hashmapstorageitems.get(name);
            present = false;
            for (Chipotlian value:chipotlians.values()) {
            	if (value.ifincurrentLoc(currentRoom)){
            		System.out.println();
            		System.out.println(value.getName()+" is in your area");
            		System.out.println("Try yo him");
            		present = true;
            	}
            }
        }
     
    }
    private void setmark()
    {
        markedRooms.add(currentRoom); //add mark current room to a marked list
        System.out.println("THOU HATH RECEIVED DA POWER TO MOONWALK!");
        System.out.println("You have set a mark on "+currentRoom.getShortDescription());
    }
  //method for comparison with  input room name and target room object
    private boolean HMequalsnextroom(String roomname,Room target) {
    	return (hashmaprooms.get(roomname).getShortDescription()).equals(target.getShortDescription());
    }
    private boolean fasttravel()
    {
    	Random randloc = new Random();
        if (markedRooms.isEmpty()){
            System.out.println("booo,u need to leave a magic avocado to fast travel back to a place, remember?");
            return false;
        }
        for (Chipotlian v:chipotlians.values()) {
			if (questcharacternames.contains(v.getName())) {
				
			}
			else {
				v.move(rooms.get(randloc.nextInt(20)));
			}
        	
        }
        
        currentRoom = markedRooms.get(markedRooms.size()-1);
        
        System.out.println("Hooha, Ur back!Told ya moonwalking is fun, hooked on this feeeeling");
        System.out.println(currentRoom.getLongDescription());
        String name = getHashMapRoomsKey(currentRoom);
        itemsInCurrentLoc = hashmapstorageitems.get(name);
        present = false;
        for (Chipotlian value:chipotlians.values()) {
        	if (value.ifincurrentLoc(currentRoom)){
        		System.out.println();
        		System.out.println(value.getName()+" is in your area");
        		System.out.println("Try yo him");
        		present = true;
        	}
        }
        return true;
    }


	/** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
   
    
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    /**
     * check if all documents are collected (one of the end game objective)
     * @return true if all collected
     */
    private boolean check() {
    	ArrayList<Docs> a = alldocs;
    	if (notebook.containsAll(a)) {
    		System.out.println("You found all the clues!");
    		return true;
    	}
		return false;
    	
    }
}
