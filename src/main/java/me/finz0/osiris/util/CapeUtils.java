package me.finz0.osiris.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CapeUtils {
    List<UUID> uuids = new ArrayList<>();
    public CapeUtils(){
        uuids.add(UUID.fromString("4f87949a-d345-448b-b2ff-6c9134ca754e")); //FINZ0
        uuids.add(UUID.fromString("436c221b-f99f-4581-915d-58c70787f8dc")); //LolpoxMan
        uuids.add(UUID.fromString("70a2ddc6-e073-44a4-b11b-c2298031ed06")); //Novola
        uuids.add(UUID.fromString("41395139-cc96-4357-8d19-3654a29485c5")); //Novola 2: electric boogaloo
        uuids.add(UUID.fromString("d91a7113-1143-4357-ab4d-b9e5a125f933")); //Titunu
        uuids.add(UUID.fromString("d03f9cac-5fa7-45e6-80f9-7f05e05a70d9")); //Cryrobyte
        uuids.add(UUID.fromString("eba7ae93-bf5e-4c28-a625-08768d341609")); //Distaf
    }

    public boolean hasCape(UUID id){
        return uuids.contains(id);
    }
}
