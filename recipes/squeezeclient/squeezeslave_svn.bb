SECTION = "console/multimedia"
DESCRIPTION = "command line audio playback client for SlimServer"
LICENSE = "GPL"
DEPENDS += "flac libvorbis \
           ${@base_conditional('ENTERPRISE_DISTRO', '1', '', 'libmad', d)}"
SRCREV=30572
PV = "7.6+svnr${SRCPV}"

SRC_URI = "svn://svn.slimdevices.com/repos/slim/7.6/trunk/;module=softsqueeze;proto=http \
           file://varinit.patch \
           file://runfix.patch \
           file://makefile.patch"


S = "${WORKDIR}/softsqueeze/SlimProtoLib"

CFLAGS += "-D_GNU_SOURCE"

inherit autotools

do_compile() {
        oe_runmake LDFLAGS=${TARGET_LDFLAGS} -f makefile.linux
}

do_install() {
        install -d ${D}/${bindir}
        install -m 0755 bin/squeezeslave ${D}/${bindir}
    	oe_libinstall -C lib -so libslimproto ${D}${libdir}
    	oe_libinstall -C lib -so libportaudio ${D}${libdir}
    	oe_libinstall -C lib -so libportmixer ${D}${libdir}
}

