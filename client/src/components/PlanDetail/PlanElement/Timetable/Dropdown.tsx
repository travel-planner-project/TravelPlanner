import styles from './Dropdown.module.scss'

type DropdownProps = {
  handleOption: React.Dispatch<React.SetStateAction<string>>
  options: string[]
  selectedOption: string
  closeDropdown: () => void
}

function Dropdown({ handleOption, options, selectedOption, closeDropdown }: DropdownProps) {
  const selectOption = (option: string) => {
    handleOption(option)
    closeDropdown()
  }

  return (
    <div className={styles.container}>
      {options.map(opt => (
        <button
          className={opt === selectedOption ? styles.selectedOpt : styles.option}
          type='button'
          key={opt}
          onClick={() => selectOption(opt)}
        >
          {opt}
        </button>
      ))}
    </div>
  )
}

export default Dropdown
