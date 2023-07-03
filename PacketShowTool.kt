class PacketShowTool : JavaPlugin() {

    override fun onEnable() {
        // 1.19.4 기준임
        server.pluginManager.registerEvents(object : Listener {

            @EventHandler
            fun onJoin(event: PlayerJoinEvent) {
                val player = event.player
                val entityPlayer = (player as CraftPlayer).playerConnection

                val packetShowTool = PacketShowTool()

                val networkManagerField = PlayerConnection::class.java.getDeclaredField("h")
                networkManagerField.isAccessible = true
                val networkManager = networkManagerField.get(entityPlayer) as NetworkManager

                networkManager.m.pipeline().addBefore("packet_handler", "z", packetShowTool)
            }
        }, this)
    }

    override fun onDisable() {
        
    }
}

class PacketShowTool : ChannelDuplexHandler() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println("Read: ${msg::class.simpleName}")
        super.channelRead(ctx, msg)
    }
    
    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        println("Write: ${msg::class.simpleName}")
        super.write(ctx, msg, promise)
    }
}
