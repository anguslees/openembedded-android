DESCRIPTION = "RFC2425 MIME Directory Profile library, supporting vCard and iCalendar"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "LGPL"
DEPENDS = "intltool-native"
PR = "r1"

SRC_URI = "${GPE_MIRROR}/libmimedir-snapshot-20040307.tar.gz"
S = "${WORKDIR}/libmimedir"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-gtk-doc"

headers = "mimedir-attachment.h mimedir-attendee.h mimedir-attribute.h mimedir-datetime.h mimedir-init.h mimedir-period.h mimedir-profile.h mimedir-recurrence-rule.h mimedir-recurrence.h mimedir-valarm.h mimedir-vcal.h mimedir-vcard-address.h mimedir-vcard-email.h mimedir-vcard-phone.h mimedir-vcard.h mimedir-vcomponent.h mimedir-vevent.h mimedir-vfreebusy.h mimedir-vjournal.h mimedir-vtimezone.h mimedir-vtodo.h mimedir.h"

do_stage() {
	install -d ${STAGING_INCDIR}/mimedir-1.0/mimedir
	for i in ${headers}; do
		install -m 0644 ${S}/mimedir/$i ${STAGING_INCDIR}/mimedir-1.0/mimedir/$i
	done

	oe_libinstall -so -C mimedir libmimedir-0.2 ${STAGING_LIBDIR}
	ln -sf libmimedir-0.2.so ${STAGING_LIBDIR}/libmimedir.so
}

SRC_URI[md5sum] = "7c24a8ec76ad3342c642ac32976c8b04"
SRC_URI[sha256sum] = "9841831972bebef827348a9f9b0875d8dd0f5079b0f1cb6b8283ceb43befd584"
