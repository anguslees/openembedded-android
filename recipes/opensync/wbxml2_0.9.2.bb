DESCRIPTION = "WBXML parsing and encoding library"
LICENSE = "LGPL"

DEPENDS = "expat popt"

SRC_URI = "${SOURCEFORGE_MIRROR}/wbxmllib/${PN}-${PV}.tar.gz \
	   file://00-fix-includes.patch \
	   file://01-anonymous-support-and-misc-fixes.patch \
	   file://02-namespaces.patch \
	   file://04_saxlike_entity_parsing.patch \
	   file://05-syncml-fixes.patch \
	   file://06-no-install-docs.patch \
	   file://07-current_attr_null.patch \
	   file://08-maxsize-translation-table.patch;striplevel=0 \
	   "

inherit autotools pkgconfig

do_stage() {
	autotools_stage_all
}

PACKAGES += "${PN}-tools"

FILES_${PN}-tools = "${bindir}"
FILES_${PN} = "${libdir}/*.so.*"




SRC_URI[md5sum] = "67a48fd9b69db8818a4dca5375c7993a"
SRC_URI[sha256sum] = "cb4909d8753dff7f6ac90a62a61ed1ae68e49ead2dbc60c22673c55bb0e14a36"
