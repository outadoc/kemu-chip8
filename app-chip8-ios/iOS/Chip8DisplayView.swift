//
//  Chip8DisplayView.swift
//  app-chip8-ios (iOS)
//
//  Created by Baptiste on 30/11/2020.
//

import Foundation
import UIKit
import lib_kemu
import lib_chip8

class Chip8DisplayView : UIView, FrameConsumer {
    
    private let scaleFactor = 10
    private var currentFrame: UByteArray2? = nil

    private var background: Color
    private var foreground: Color

    var theme: Theme = .whiteOnBlack {
        didSet {
            background = theme.background
            foreground = theme.foreground
            setNeedsDisplay()
        }
    }

    public required init?(coder aDecoder: NSCoder) {
        background = theme.background
        foreground = theme.foreground
        super.init(coder: aDecoder)
    }

    public override init(frame: CGRect) {
        background = theme.background
        foreground = theme.foreground
        super.init(frame: frame)
    }

    func displayFrame(frame: UByteArray2?) {
        // TODO
        setNeedsDisplay()
    }
    
    override var intrinsicContentSize: CGSize {
        return CGSize(
            width: (Int(Chip8Constants().DISPLAY_WIDTH) * scaleFactor),
            height: (Int(Chip8Constants().DISPLAY_HEIGHT) * scaleFactor)
        )
    }
}
