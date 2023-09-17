import SpriteIcon from '../../assets/sprite-icon.svg'

type IconNameType =
  | 'chatting-plus'
  | 'chatting-dots'
  | 'box-arrow-left'
  | 'box-arrow-right'
  | 'calendar'
  | 'camera'
  | 'check-box'
  | 'uncheck-box'
  | 'x'
  | 'skyblue-x-circle'
  | 'pencil-box'
  | 'trash-bin'
  | 'alarm-gray'
  | 'alarm-blue'
  | 'calculator-gray'
  | 'calculator-blue'
  | 'file-text-fill-gray'
  | 'file-text-fill-blue'
  | 'check-square-gray'
  | 'check-square-blue'
  | 'add-person'
  | 'delete-person'
  | 'profile'
  | 'eye'
  | 'eye-close'
  | 'minus-square'
  | 'plus-square'
  | 'arrow-down'
  | 'x-circle-blue'
  | 'x-circle-red'

type IconProps = {
  name: IconNameType
  size?: number
  className?: string
}

function Icon({ name, size = 24, className = '' }: IconProps) {
  return (
    <div className={className}>
      <svg width={size} height={size}>
        <use href={`${SpriteIcon}#${name}`} />
      </svg>
    </div>
  )
}

export default Icon
