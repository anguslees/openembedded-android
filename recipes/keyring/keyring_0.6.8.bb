DESCRIPTION = "Stores accounts and passwords in an encrypted file."
SECTION = "opie/applications"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "gdbm"
PR = "r2"

SRC_URI = "http://www.scrypt.net/~celer/kweb/Keyring-0.6.8.tgz \
	   file://keyring-0.6.8-datatype.patch"
S = "${WORKDIR}/Keyring"

inherit palmtop

QPEDIR = "${OPIEDIR}"

do_install() {
        install -d ${D}${palmtopdir}/bin \
        	   ${D}${palmtopdir}/apps/Applications \
        	   ${D}${palmtopdir}/pics/keyring
        install -m 0755 keyring ${D}${palmtopdir}/bin/
        install -m 0644 keyring.desktop \
			${D}${palmtopdir}/apps/Applications/
        install -m 0644 ${S}/Keyring.png \
			${S}/keyring-pwlist.png \
			${D}${palmtopdir}/pics/

	# The following should appear in an assoicated DOC package.

	install -d ${D}${palmtopdir}/help/html
        install -m 0644 ${S}/*.html ${D}${palmtopdir}/help/html/
}

PACKAGES = "${PN}-dbg ${PN} ${PN}-help"
FILES_${PN} = " ${palmtopdir}${base_bindir} ${palmtopdir}/apps ${palmtopdir}/pics"
FILES_${PN}-help = " ${palmtopdir}/help/html"


SRC_URI[md5sum] = "0f429212fdcfd99efe6bfd3904adece8"
SRC_URI[sha256sum] = "272ddac7f039d12938f50c281f42941d6dea7ab940f1b3b3dd5ab28572678bd4"
