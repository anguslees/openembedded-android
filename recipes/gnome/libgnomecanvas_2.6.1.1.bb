LICENSE = "GPL"
SECTION = "x11/gnome/libs"

PR = "r2"

DESCRIPTION = "A powerful object-oriented display"
inherit gnome

DEPENDS = "libglade libart-lgpl"

EXTRA_OECONF = "--disable-gtk-doc"

FILES_${PN} += "${libdir}/libglade/*/libcanvas.so"

SRC_URI[archive.md5sum] = "040257b0231fd5fc507f731d73d9738b"
SRC_URI[archive.sha256sum] = "fd117570a94e527f76241bc54d2dd53cbea5e17455f5795b5e52afee25eccaca"
