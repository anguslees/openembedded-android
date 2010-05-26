SECTION = "console/network"
DEPENDS = "openssl"
DESCRIPTION = "Extremely simple MTA to get mail off the system to a mail hub."
LICENSE = "GPL"
PR = "r6"

SRC_URI = "${DEBIAN_MIRROR}/main/s/ssmtp/ssmtp_${PV}.orig.tar.gz \
           file://ldflags.patch \
           file://configure.patch \
           file://libs-lcrypto.patch \
           file://dont-strip.patch \
           file://ssmtp.conf"
S = "${WORKDIR}/${PN}-${PV}"

inherit autotools

CONFFILES_${PN} = "${sysconfdir}/ssmtp/ssmtp.conf ${sysconfdir}/ssmtp/revaliases"
EXTRA_OECONF = "--enable-ssl"
INHIBIT_AUTO_STAGE = "1"

do_compile () {
        oe_runmake 'LDFLAGS=${LDFLAGS}'
}

do_install () {
        oe_runmake 'prefix=${D}${prefix}' 'exec_prefix=${D}${exec_prefix}' \
                   'bindir=${D}${bindir}' 'mandir=${D}${mandir}' \
                   'etcdir=${D}${sysconfdir}' GEN_CONFIG="`which echo`" install
        install -d ${D}${sysconfdir}/ssmtp
        install -m 0644 ${WORKDIR}/ssmtp.conf ${D}${sysconfdir}/ssmtp/ssmtp.conf
}

pkg_postinst () {
        update-alternatives --install ${sbindir}/sendmail sendmail ${bindir}/ssmtp 30
}

pkg_postrm () {
        update-alternatives --remove ${sbindir}/sendmail sendmail
}

SRC_URI[md5sum] = "957e6fff08625fe34f4fc33d0925bbc9"
SRC_URI[sha256sum] = "2151ad18cb73f9a254f796dde2b48be7318b45410b59fedbb258db5a41044fb5"
