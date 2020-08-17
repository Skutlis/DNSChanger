Start Main -java file to run the program. 
The DNSChanger changes the DNS to one of the serves added (Cloudflare, Google, OpenDNS and Quad9) on selection.
More servers can simply be added by implementing a new class using the IdnsServers interface (Have a look at the google file for an example)
then go to the DNS file and add a *new "servername"()* in the constructor, and you are all set. 