SUMMARY = "tcllib is a Tcl-only library of standard routines for Tcl (no compiling required)."
HOMEPAGE = "http://tcllib.sourceforge.net"
SECTION = "devel/tcltk"
LICENSE = "tcl"

DEPENDS = 'tcl'

S = "${WORKDIR}/${PN}-${PN}_1_16"

LIC_FILES_CHKSUM = "\
    file://README;md5=b06a7331a8bd84715752295f640c1779 \
"
SRC_URI= "https://github.com/tcltk/tcllib/archive/tcllib_1_16.tar.gz"


SRC_URI[md5sum] = "6ebc460a3f5bfb09eb722bf123165c24"
SRC_URI[sha256sum] = "f500a3ee2082e105693e775c8d94d9f3ab5ae687f706a7b0f6d89ec884332456"


