package net.feliscape.hops_and_barrels.networking.packets;

import net.feliscape.hops_and_barrels.client.ClientSiphonData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SiphonDataSyncS2CPacket {
    private final int siphonedHearts;

    public SiphonDataSyncS2CPacket(int siphonedHearts){
        this.siphonedHearts = siphonedHearts;
    }

    public SiphonDataSyncS2CPacket(FriendlyByteBuf buf){
        this.siphonedHearts = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(siphonedHearts);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client-side
            ClientSiphonData.set(siphonedHearts);
        });
        return true;
    }
}
