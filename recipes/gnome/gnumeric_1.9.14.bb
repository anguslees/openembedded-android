LICENSE = "GPL"
SECTION = "x11/utils"
DEPENDS = "gdk-pixbuf-csource-native libgsf gtk+ libxml2 goffice libglade libart-lgpl intltool-native libgnomecanvas libgnomeprint libgnomeprintui libbonoboui orbit2-native"
DESCRIPTION = "Gnumeric spreadsheet for GNOME"

PARALLEL_MAKE = ""

inherit gnome flow-lossage

SRC_URI += " file://nodolt.patch "

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

SRC_URI[archive.md5sum] = "6781d0df02e603c556758d90e96e37f6"
SRC_URI[archive.sha256sum] = "0f934eddd2204f938f0359d8c39ef590ab4fec60d6922853e6c8c23e3069d7f4"
