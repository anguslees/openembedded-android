DESCRIPTION = "KDE Web Browser Konqueror, QtE based Palmtop Environments Edition"
HOMEPAGE = "http://www.konqueror.org/embedded"
SECTION = "opie/applications"
PRIORITY = "optional"
DEPENDS = "libqpe-opie openssl libpcre"
LICENSE = "LGPL GPL"
PR = "r5"

# Note if this .bb files fails with the error:
# "No rule to make target `-lpcre', needed by `konqueror'.  Stop."
# a workaround is to install the libpcre3-dev package onto your build
# machine (Ubuntu/Debain) or your distro's equivalent (FC = pcre-devel).

SRC_URI = "http://devel-home.kde.org/~hausmann/snapshots/Attic/konqueror-embedded-snapshot-${PV}.tar.gz \
           file://opie1.patch \
           file://packing.patch \
           file://include_qconfig.patch \
           file://useragent.patch \
           file://kcookiejar-merge.patch \
           file://malformed.patch \
           file://cachepath.patch \
           file://vit.patch \
           file://gcc4.patch \
           file://konq-embedrc"
S = "${WORKDIR}/konqueror-embedded-snapshot-${PV}"

inherit autotools

FILES_${PN} = "${palmtopdir}"

export QMAKE = "${STAGING_BINDIR_NATIVE}/qmake"
export MOC = "${STAGING_BINDIR_NATIVE}/moc"
export UIC = "${STAGING_BINDIR_NATIVE}/uic"

EXTRA_OECONF = '--prefix=${palmtopdir} --exec-prefix=${palmtopdir}                              \
                --enable-static --disable-shared --disable-debug                                \
                --with-javascript=static --enable-qpe --enable-qt-embedded                      \
                --with-extra-includes=${STAGING_INCDIR} --with-extra-libs=${STAGING_LIBDIR}     \
                --with-ssl-version=0.9.7c --with-ssl-dir=${STAGING_LIBDIR}/..                   \
                --with-qt-dir=${QTDIR} --with-qtopia-dir=${OPIEDIR}                             \
		--enable-libsuffix=""							        '

CXXFLAGS += "-DOPIE_NO_ERASE_RECT_HACKFIX -DOPIE_NEW_MALLOC"

do_compile_prepend() {
	perl admin/am_edit
}

do_install() {
    install -d ${D}${palmtopdir}/share/
    install -d ${D}${palmtopdir}/share/config/

    install -m 0644 ${WORKDIR}/konq-embedrc ${D}${palmtopdir}/share/config/

    autotools_do_install
}

SRC_URI[md5sum] = "30dc3e109124e8532c7c0ed6ad3ec6fb"
SRC_URI[sha256sum] = "305a7178d3351ea5e1faad157c2f056b633fbbabc006e19165a55314e9352d7b"
