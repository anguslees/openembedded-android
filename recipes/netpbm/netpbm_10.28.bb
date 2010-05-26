DESCRIPTION = "Netpbm is a toolkit for manipulation of graphic images, including\
conversion of images between a variety of different formats.  There\
are over 220 separate tools in the package including converters for\
about 100 graphics formats."
HOMEPAGE = "http://netpbm.sourceforge.net"
SECTION = "console/utils"
LICENSE = "GPL MIT Artistic"
# NOTE: individual command line utilities are covered by different
# licenses.  The compiled and linked command line utilties are
# subject to the licenses of the libraries they use too - including
# libpng libz, IJG, and libtiff licenses
DEPENDS = "jpeg zlib libpng tiff flex-native"
RDEPENDS = "perl\
	perl-module-cwd\
	perl-module-english\
	perl-module-fcntl\
	perl-module-file-basename\
	perl-module-file-spec\
	perl-module-getopt-long\
	perl-module-strict\
	"

# these should not be required, they are here because the perl
# module dependencies are currently incorrect:
RDEPENDS += "perl-module-exporter-heavy"
RDEPENDS += "perl-module-file-spec-unix"

PR = "r7"

SRC_URI = "${SOURCEFORGE_MIRROR}/netpbm/netpbm-${PV}.tgz \
	   file://ppmtojpeg.patch;striplevel=42 \
	   file://libgnuhash.patch \
	   file://Makefile.config \
	   file://oeendiangen"

PARALLEL_MAKE = ""

EXTRA_OEMAKE = "ENDIANGEN=${S}/buildtools/oeendiangen TARGET_LD=${LD} 'STRIPFLAG='"

do_configure() {
	install -c -m 644 ../Makefile.config .
	# The following stops the host endiangen program being run and uses
	# the target endian.h header instead.
	install -c -m 755 ../oeendiangen buildtools
}

do_compile() {
	# need all to get the static library too
	oe_runmake all default
}

do_install() {
	# netpbm makes its own installation package, which must then be
	# installed to form the dummy installation for ipkg
	rm -rf ${WORKDIR}/netpbm-package
	oe_runmake package pkgdir=${WORKDIR}/netpbm-package
	# now install the stuff from the package into ${D}
	for d in ${WORKDIR}/netpbm-package/*
	do
		# following will cause an error if used
		case "$d" in
		*/README)	;;
		*/VERSION)	;;
		*/pkginfo)	;;
		*/bin)		install -d ${D}${bindir}
				cp -pPR "$d"/* ${D}${bindir}
				rm ${D}${bindir}/doc.url;;
		*/include)	install -d ${D}${includedir}
				cp -pPR "$d"/* ${D}${includedir};;
		*/link|*/lib)	install -d ${D}${libdir}
				cp -pPR "$d"/* ${D}${libdir};;
		*/man)		install -d ${D}${mandir}
				cp -pPR "$d"/* ${D}${mandir};;
		*/misc)		install -d ${D}${datadir}/netpbm
				cp -pPR "$d"/* ${D}${datadir}/netpbm;;
		*/config_template)
				install -d ${D}${bindir}
				sed "/^@/d
					s!@VERSION@!$(<'${WORKDIR}/netpbm-package/VERSION')!
					s!@DATADIR@!${datadir}/netpbm!
					s!@LIBDIR@!${libdir}!
					s!@LINKDIR@!${libdir}!
					s!@INCLUDEDIR@!${includedir}!
					s!@BINDIR@!${bindir}!
					" "$d" >${D}${bindir}/netpbm-config
				chmod 755 ${D}${bindir}/netpbm-config;;
		*)		echo "netpbm-package/$d: unknown item" >&2
				exit 1;;
		esac
	done
}

SRC_URI[md5sum] = "18ed6d0f1adf75a748df55d5e924fabb"
SRC_URI[sha256sum] = "e42d3b3f30de0c1ad0a86aa039b35cf2bf64c7f64c29ee333cbf934784639973"
