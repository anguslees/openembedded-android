require cornucopia.inc
inherit fso-plugin
SRCREV = "${FSO_CORNUCOPIA_SRCREV}"
PV = "0.1.0.0+gitr${SRCPV}"
PE = "1"
PR = "${INC_PR}.3"
RDEPENDS += "iptables"
