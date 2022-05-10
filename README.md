# hashcatnick
P2P network for cracking passwords by their hash.

This project proposes opportunity to distribute computation of password between peers.

To start the main node start your jar file with argument main like this:
* java -jar hashcatnick.jar main

Also, you can choose a port on which to start, i.e.:
* java -jar hashcatnick.jar 1234
* java -jar hashcatnick.jar 4321 main

To start cracking password you need to use this command:

* hashcat {passwordHash} {hashAlgorithm}

The project uses HTTP for communication