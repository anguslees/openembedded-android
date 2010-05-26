LICENSE = "GPL"
SECTION = "x11/utils"
DEPENDS = "gdk-pixbuf-csource-native libgsf gtk+ libxml2 goffice libglade libart-lgpl intltool-native libgnomecanvas libgnomeprint libgnomeprintui libbonoboui orbit2-native"
DESCRIPTION = "Gnumeric spreadsheet for GNOME"

PARALLEL_MAKE = ""

inherit gnome flow-lossage

SRC_URI += " file://nodolt.patch "

SRC_URI[archive.md5sum] = "80daf446f299578d05e60e9b7d38900c"
SRC_URI[archive.sha256sum] = "91a0a9aa47ae82b14fe717e5d49d164cfe871add2bf71d0cb2f68cf3d383c9db"

EXTRA_OECONF=" --without-perl "

PACKAGES_DYNAMIC += "gnumeric-plugin-*"

FILES_${PN}-dbg += "${libdir}/gnumeric/${PV}/plugins/*/.debug"
FILES_${PN} += "${libdir}/libspreadsheet-${PV}.so "

do_configure_prepend() {
	sed -i -e s:doc\ tools:tools: ${S}/Makefile.am
}

# We need native orbit-idl with target idl files. No way to say it in a clean way:
do_configure_append () {
	find -name Makefile -exec sed -i '/\/usr\/bin\/orbit-idl-2/{s:/usr/bin:${STAGING_BINDIR_NATIVE}:;s:/usr/share:${STAGING_DATADIR}:g}' {} \;
}

python populate_packages_prepend () {
	gnumeric_libdir = bb.data.expand('${libdir}/gnumeric/${PV}/plugins', d)

	do_split_packages(d, gnumeric_libdir, '(.*)', 'gnumeric-plugin-%s', 'Gnumeric plugin for %s', allow_dirs=True)
}
