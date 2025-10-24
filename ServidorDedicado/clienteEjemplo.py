import asyncio
import websockets
import json

uri = "ws://automundo.ddns.net:8765"

async def check_server_status():
    """
    Se conecta al servidor y envía un mensaje "check_lobby".
    """
    print(f"Intentando conectar a {uri}...")
    
    try:
        async with websockets.connect(uri) as websocket:
            
            message_to_send = {
                "type": "check_lobby"
            }
            
            await websocket.send(json.dumps(message_to_send))
            print(f"> Mensaje enviado: {message_to_send}")
            
            response_str = await websocket.recv()
            response = json.loads(response_str)
            
            print(f"< Respuesta del servidor: {response}")
            

    except websockets.exceptions.ConnectionClosedError:
        print("Conexión cerrada por el servidor.")
    except Exception as e:
        print(f"No se pudo conectar o enviar el mensaje.")


# --- Para correr el cliente ---
if __name__ == "__main__":
    asyncio.run(check_server_status())