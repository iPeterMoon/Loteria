import asyncio
import websockets
import json

# --- Estado del Servidor ---
players = {}
player_limit = 0
is_lobby_open = True
GAME_PORT = 9000 # Puerto de los clientes

# Enviar la lista de IPs a todos
async def broadcast_player_list():
    if not players:
        return
        
    # Crear una lista simple de todas las IPs
    player_ips = list(players.values())
    
    message = json.dumps({
        "type": "player_list", 
        "players": player_ips,
        "game_port": GAME_PORT 
    })
    
    # Enviar la lista a los jugadores conectados
    for client_socket in players.keys():
        await client_socket.send(message)

# Función para manejar cada jugador
async def handle_player(websocket, path):
    global player_limit, is_lobby_open
    
    player_ip = websocket.remote_address[0]
    intent_type = None

    try:
        # 1. Esperar el primer mensaje (la "intención")
        message_str = await websocket.recv()
        message = json.loads(message_str)
        intent_type = message.get("type")

        # --- INTENCIÓN 1: Solo checar si existe un lobby ---
        if intent_type == "check_lobby":
            print(f"IP {player_ip} está checando el lobby.")
            status = {
                "type": "lobby_status",
                "exists": player_limit > 0,
                "current_players": len(players),
                "player_limit": player_limit
            }
            await websocket.send(json.dumps(status))
            await websocket.close()
            return

        # --- INTENCIÓN 2: Crear el lobby ---
        elif intent_type == "set_limit":
            # Verifica si existe un lobby
            if players:
                print(f"Rechazando creación de {player_ip}, lobby ya existe.")
                await websocket.send(json.dumps({"type": "error", "message": "El lobby ya existe."}))
                await websocket.close()
                return
            
            player_limit = int(message.get("limit", 4))
            print(f"Lobby CREADO por {player_ip}. Límite: {player_limit}")
            is_lobby_open = True

        # --- INTENCIÓN 3: Unirse a un lobby existente ---
        elif intent_type == "join_lobby":
            if player_limit == 0:
                print(f"Rechazando unión de {player_ip}, no hay lobby.")
                await websocket.send(json.dumps({"type": "error", "message": "No hay un lobby creado."}))
                await websocket.close()
                return
            if len(players) >= player_limit:
                print(f"Rechazando unión de {player_ip}, lobby lleno.")
                await websocket.send(json.dumps({"type": "error", "message": "Lobby lleno."}))
                await websocket.close()
                return
        
        else:
            await websocket.send(json.dumps({"type": "error", "message": "Intención desconocida."}))
            await websocket.close()
            return

    except Exception as e:
        print(f"Error en la conexión inicial de {player_ip}: {e}")
        await websocket.close()
        return
        
    # Añadir jugador al lobby
    print(f"Jugador {player_ip} aceptado en el lobby. Total: {len(players) + 1}/{player_limit}")
    players[websocket] = player_ip

    await broadcast_player_list()

    if len(players) == player_limit:
        print("Lobby lleno. La partida puede comenzar.")
        is_lobby_open = False 

    try:
        await websocket.wait_closed()
    finally:
        print(f"Jugador {player_ip} desconectado.")
        if websocket in players:
            del players[websocket]
            await broadcast_player_list()
            
            if not players:
                player_limit = 0
                is_lobby_open = True
                print("Lobby vacío. Esperando al primer jugador...")

# --- Iniciar el servidor ---
async def main():
    async with websockets.serve(handle_player, "0.0.0.0", 8765):
        print("Servidor de Lobby LAN iniciado en el puerto 8765...")
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())