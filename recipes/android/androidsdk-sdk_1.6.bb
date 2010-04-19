DESCRIPTION = "Android SDK"
SECTION = "libs"

DEPENDS += "sed-native"
RDEPENDS += "android-tools-sdk"
# Make end user provide this themselves
# RDEPENDS += virtual/java-native
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

PV = 1.6_r02
SRC_URI = "http://dl.google.com/android/repository/android-${PV}-${BUILD_OS}.zip;name=sdk"
SRC_URI[sdk.md5sum] = "b0960a1abfcf4e875528a5dc5dada071"
SRC_URI[sdk.sha256sum] = "b17696b7c0bb26f48095422611252145eb4e06335b7c5197fe383a685914653d"

S = "${WORKDIR}/android-${PV}-${BUILD_OS}"

do_compile() {
	:
}

bins = "dexdump aapt aidl"
java = "dx"

do_install() {
	install -d ${D}${bindir}
	for p in ${bins}; do
		install -m 0755 "${S}"/tools/$p ${D}${bindir}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${datadir}/java,' "${S}"/tools/$p >${D}${bindir}/$p
		chmod +x ${D}${bindir}/$p
	done

	install -d ${D}${datadir}/emulator
	cp -R -pf "${S}"/skins ${D}${datadir}/emulator
	cp -R -pf "${S}"/images ${D}${datadir}/emulator
	chmod a+rX -R ${D}${datadir}/emulator

	oe_jarinstall ${S}/android.jar
	oe_jarinstall ${S}/tools/lib/dx.jar
}
