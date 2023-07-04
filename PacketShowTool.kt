class PacketShowTool : JavaPlugin() {

    // 1.19.4 기준임
    override fun onEnable() {
        server.onlinePlayers.forEach {
            val packetShowTool = PacketShowTool()
            it.playerConnection.getNetworkManager().addHandler(packetShowTool)
        }

        server.pluginManager.registerEvents(object : Listener {

            @EventHandler
            fun onJoin(event: PlayerJoinEvent) {
                val player = event.player
                val playerConnection = (player as CraftPlayer).handle.b
                val packetShowTool = PacketShowTool()
                playerConnection.getNetworkManager().addHandler(packetShowTool)
            }

            @EventHandler
            fun onQuit(event: PlayerQuitEvent) {
                val player = event.player
                val playerConnection = (player as CraftPlayer).handle.b
                playerConnection.getNetworkManager().removeHandler()
            }
        }, this)
    }

    override fun onDisable() {
        server.onlinePlayers.forEach {
            it.playerConnection.getNetworkManager().removeHandler()
        }
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

private fun PlayerConnection.getNetworkManager(): NetworkManager {
    val networkManagerField = PlayerConnection::class.java.getDeclaredField("h")
    networkManagerField.isAccessible = true
    return networkManagerField.get(this) as NetworkManager
}

private fun NetworkManager.addHandler(channelHandler: ChannelHandler) {
    m.pipeline().addBefore("packet_handler", "trade_handler", channelHandler)
}

private fun NetworkManager.removeHandler() {
    m.pipeline().remove("trade_handler")
}
