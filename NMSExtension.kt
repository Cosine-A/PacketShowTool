fun ItemStack.toCraftItemStack() = CraftItemStack.asNMSCopy(this)

fun PlayerConnection.sendPacket(packet: Packet<*>) = a(packet)

fun Player.sendPacket(packet: Packet<*>) = playerConnection.a(packet)

val Player.playerConnection: PlayerConnection get() = entityPlayer.b

val Player.craftPlayer: CraftPlayer get() = this as CraftPlayer

val Player.entityPlayer: EntityPlayer get() = craftPlayer.handle

val Player.craftWorld: CraftWorld get() = this.world as CraftWorld

val Player.worldServer: WorldServer get() = craftWorld.handle
