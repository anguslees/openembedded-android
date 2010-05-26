SECTION = "libs"
DEPENDS = ""
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/openjade/OpenSP-${PV}.tar.gz \
		file://m4.patch \
		file://attributevalue.patch \
		file://rangmap-fix.patch"

S = "${WORKDIR}/OpenSP-${PV}"
LICENSE = "MIT"
inherit autotools native

EXTRA_OECONF = "\
	--enable-default-catalog=${sysconfdir}/sgml/catalog \
	--enable-default-search-path=${datadir}/sgml \
	"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/opensp-${PV}"
PACKAGES = ""

do_configure () {
	gnu-configize
	oe_runconf
}

do_stage () {
	oe_libinstall -a -so -C lib libosp ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/OpenSP
	install -m 0644 ${S}/include/*.h ${STAGING_INCDIR}/OpenSP/
	install -m 0644 ${S}/include/*.cxx ${STAGING_INCDIR}/OpenSP/
	install -m 0644 config.h ${STAGING_INCDIR}/OpenSP/config.h

	# Installs all the tools and symlink with another common name to each.
	install -d ${STAGING_BINDIR_NATIVE}
	for tool in nsgmls sgmlnorm spam spcat spent sx
	do
		echo installing $tool to ${STAGING_BINDIR_NATIVE}
		install -m 0755 $tool/.libs/o$tool ${STAGING_BINDIR_NATIVE}
		ln -sf o$tool ${STAGING_BINDIR_NATIVE}/$tool
	done
}

SRC_URI[md5sum] = "87f56e79ae0c20397f4207d61d154303"
SRC_URI[sha256sum] = "987eeb9460185950e066e5db3b5fa531e53e213742b545288405552a5a7bb704"
