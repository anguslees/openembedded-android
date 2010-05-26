DESCRIPTION = "Qt/Embedded bindings for the Scintilla source code editor component"
SECTION = "opie/libs"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "http://www.mneuroth.de/privat/zaurus/qscintilla-${PV}_zaurus.tar.gz \
           file://no-external-lexers.patch;striplevel=0"

S = "${WORKDIR}/qscintilla-${PV}/qt"

inherit opie

QMAKE_PROFILES = "qscintilla.pro"

EXTRA_QMAKEVARS_POST += "INCLUDEPATH+=${S}/patches \
                         DEFINES+=ZPATCH DEFINES+=ZAURUS \
			 HEADERS-=qextscintillaprinter.h \
			 SOURCES-=qextscintillaprinter.cpp \
			 SOURCES+=patches/qsettings.cpp \
			 SOURCES+=patches/qsettings_unix.cpp \
			 HEADERS+=patches/qsettings.h"

PARALLEL_MAKE = ""

do_stage() {
	install -m 0644 qextscintilla*.h ${STAGING_INCDIR}/
	oe_libinstall -so libqscintilla ${STAGING_LIBDIR}
}

do_install() {
	install -d ${D}${libdir}
	oe_libinstall -so libqscintilla ${D}${libdir}
}

FILES_${PN} = "${libdir}"

SRC_URI[md5sum] = "999d3a8b916cd1ef13a66843f6f26db7"
SRC_URI[sha256sum] = "e828dc4aaa7948eafee343e70190dd8003498d50d9258d75d47f05f9970683db"
