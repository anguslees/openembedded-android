require contacts.inc

#DEPENDS += "gnome-vfs"
#RDEPENDS += "gnome-vfs-plugin-file"
#RRECOMMENDS += "gnome-vfs-plugin-http"

PV = "0.8+svnr${SRCPV}"

DEFAULT_PREFERENCE = "-1"

SRC_URI = "svn://svn.o-hand.com/repos/${PN};module=trunk;proto=http \
	   file://stock_contact.png \
	   file://stock_person.png \
	   file://contacts-owl-window-menu.patch \
	  "

S = "${WORKDIR}/trunk"

#EXTRA_OECONF = "--enable-gnome-vfs"
