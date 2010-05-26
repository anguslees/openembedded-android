DESCRIPTION = "Full-featured IRC chat client with scripting support"
LICENSE = "GPL"
HOMEPAGE = "http://www.xchat.org"
SECTION = "x11/network"
DEPENDS = "libgcrypt zlib gtk+"
DEPENDS += "gdk-pixbuf-csource-native"
PR = "r2"

SRC_URI = "http://www.xchat.org/files/source/2.8/xchat-${PV}.tar.bz2 \
	 file://46_CVE-2009-0315.dpatch;apply=yes \
	 file://53_fix_deprecated_widgets.dpatch;apply=yes \
	 "

inherit autotools

EXTRA_OECONF = "\
  --disable-perl \
  --disable-python \
  --disable-tcl \
"

#Fix little bug that slipped into the 2.8.6 release, already fixed upstream.
do_compile_prepend() {
        sed -i 's/GtkType/GType/' ${s}src/fe-gtk/xtext.{c,h}
}

FILES_${PN} += "${datadir}/dbus-1"
                
FILES_${PN}-dbg += "${libdir}/xchat/plugins/.debug"

SRC_URI[md5sum] = "1f2670865d43a23a9abc596dde999aca"
SRC_URI[sha256sum] = "8c89dbf36304b99363a7f090d695447653102b4528ca2aa367a2abe5cff1746d"
