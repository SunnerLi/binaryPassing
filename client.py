import socket, os, sys

ACCPET_PORT = 8059
IMAGE_NAME = "copy.mp3"
BIT_LENGTH = 65536

def AcceptImage():
	"""
		The main subrotine to accept the image.
	"""
	#initialize the socket
	acceptSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	acceptSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	acceptSocket.bind(("", ACCPET_PORT))
	acceptSocket.listen(1)

	#loop to accept the image
	while True:
		#accept the connection
		print "wait for connection"
		operateSocket, address = acceptSocket.accept()
		print "Conencted to - " + str(address)

		#create a file
		fp = open(IMAGE_NAME, "w");

		#receive the info
		while True:
			print "receive..."
			bitInfo = operateSocket.recv(BIT_LENGTH)

			#check if the bitinfo is error
			if not bitInfo:
				break
			fp.write(bitInfo)

		#done
		print "Done"
		fp.close()
		operateSocket.close()


if __name__ == "__main__":
	AcceptImage()





